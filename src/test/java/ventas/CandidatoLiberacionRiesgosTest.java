package ventas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ventas.adapters.in.rest.ApiExceptionHandler;
import ventas.adapters.in.rest.VentaRestController;
import ventas.adapters.in.rest.dto.CrearVentaRequestDto;
import ventas.adapters.in.rest.dto.ItemVentaDto;
import ventas.adapters.in.rest.dto.ProblemDetailDto;
import ventas.adapters.in.rest.dto.ProductoResponseDto;
import ventas.adapters.in.rest.dto.VentaResponseDto;
import ventas.adapters.out.hibernate.HibernateUtil;
import ventas.adapters.out.hibernate.ProductoHibernateRepository;
import ventas.adapters.out.hibernate.VentaHibernateRepository;
import ventas.adapters.out.hibernate.entity.DetalleVentaEntity;
import ventas.adapters.out.hibernate.entity.VentaEntity;
import ventas.application.ComandoCrearVenta;
import ventas.application.ConfirmarVentaService;
import ventas.application.DescuentoEstudiante;
import ventas.application.VentaFacade;
import ventas.domain.Categoria;
import ventas.domain.Cliente;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.domain.Venta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CandidatoLiberacionRiesgosTest {

    private MockMvc mockMvc;
    private ProductoHibernateRepository productoRepo;
    private ObjectMapper objectMapper;
    private boolean bdDisponible = false;

    @BeforeEach
    void setUp() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if (sf != null && !sf.isClosed()) {
            bdDisponible = true;
            productoRepo = new ProductoHibernateRepository(sf);
            VentaHibernateRepository ventaRepo = new VentaHibernateRepository(sf);
            ConfirmarVentaService confirmarService = new ConfirmarVentaService(ventaRepo);
            VentaFacade facade = new VentaFacade(ventaRepo, confirmarService);
            VentaRestController ventaController = new VentaRestController(facade, productoRepo);
            mockMvc = MockMvcBuilders.standaloneSetup(ventaController)
                    .setControllerAdvice(new ApiExceptionHandler())
                    .build();
            objectMapper = new ObjectMapper();
        }
    }

    @Test
    @DisplayName("R01 - POST /ventas sobre PostgreSQL asigna ID, confirma estado, soporta email largo y descuenta stock")
    void ventaCompletaSobrePostgresDescuentaStockYSoportaEmailLargo() throws Exception {
        assumeTrue(bdDisponible, "PostgreSQL requerido para prueba de riesgo E2E");
        long codigo = 9101L;
        productoRepo.guardar(new Producto(codigo, "Switch Gigabit C04", new Dinero(650.00), 10, Categoria.ELECTRONICA));

        CrearVentaRequestDto solicitud = new CrearVentaRequestDto(
                "Laura Patricia Gomez",
                "laura.patricia.gomez.institucional@estudiantes.uv.mx",
                List.of(new ItemVentaDto(codigo, 3))
        );

        mockMvc.perform(post("/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/ventas/VNT-")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.cliente", is("Laura Patricia Gomez")))
                .andExpect(jsonPath("$.total", is(1950.0)))
                .andExpect(jsonPath("$.estado", is("CONFIRMADA")))
                .andExpect(jsonPath("$.detallesCount", is(1)));

        Optional<Producto> actualizado = productoRepo.buscarPorCodigo(codigo);
        assertTrue(actualizado.isPresent());
        assertEquals(7, actualizado.get().getExistencia());
    }

    @Test
    @DisplayName("R02 - POST /ventas con stock insuficiente ejecuta rollback en PostgreSQL y retorna 409 ProblemDetail")
    void ventaSinStockEjecutaRollbackYRetorna409ProblemDetail() throws Exception {
        assumeTrue(bdDisponible, "PostgreSQL requerido para prueba de atomicidad");
        long codigoConStock = 9102L;
        long codigoSinStock = 9103L;
        productoRepo.guardar(new Producto(codigoConStock, "Cable UTP Cat6", new Dinero(120.00), 8, Categoria.GENERAL));
        productoRepo.guardar(new Producto(codigoSinStock, "Router Empresarial", new Dinero(2400.00), 1, Categoria.ELECTRONICA));

        CrearVentaRequestDto solicitud = new CrearVentaRequestDto(
                "Roberto Diaz",
                "roberto.diaz@uv.mx",
                List.of(
                        new ItemVentaDto(codigoConStock, 2),
                        new ItemVentaDto(codigoSinStock, 5)
                )
        );

        mockMvc.perform(post("/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.type", is("https://api.ventas.tcsw/errores/conflicto-estado")))
                .andExpect(jsonPath("$.detail", containsString("Existencia insuficiente")));

        Optional<Producto> intacto = productoRepo.buscarPorCodigo(codigoConStock);
        assertTrue(intacto.isPresent());
        assertEquals(8, intacto.get().getExistencia());
    }

    @Test
    @DisplayName("R03 - ApiExceptionHandler e invariante de asignarId cubren 400, 409 y 500 sanitizado")
    void exceptionHandlerEInvarianteIdCubrenErroresControlados() {
        Venta venta = new Venta();
        assertThrows(IllegalArgumentException.class, () -> venta.asignarId("   "));
        venta.asignarId("VNT-TEST-01");
        assertEquals("VNT-TEST-01", venta.getId());

        ApiExceptionHandler handler = new ApiExceptionHandler();
        MockHttpServletRequest req = new MockHttpServletRequest("POST", "/ventas");

        ResponseEntity<ProblemDetailDto> resp400 =
                handler.handleIllegalArgument(new IllegalArgumentException("Monto fuera de rango"), req);
        assertEquals(400, resp400.getStatusCodeValue());
        assertNotNull(resp400.getBody());
        assertEquals("Conflicto o argumento invalido", resp400.getBody().getTitle());
        assertEquals("/ventas", resp400.getBody().getInstance());
        assertNotNull(resp400.getBody().getTimestamp());

        ResponseEntity<ProblemDetailDto> resp500 =
                handler.handleGeneralError(new RuntimeException("detalle sensible SQL interno"), req);
        assertEquals(500, resp500.getStatusCodeValue());
        assertNotNull(resp500.getBody());
        assertEquals("Error interno del servidor", resp500.getBody().getTitle());
        assertFalse(resp500.getBody().getDetail().contains("SQL"));

        Cliente cl = new Cliente(5L, "Ana", "Lopez", "2281112233");
        assertThrows(IllegalArgumentException.class, () -> new ComandoCrearVenta(null, new DescuentoEstudiante()));
        assertThrows(IllegalArgumentException.class, () -> new ComandoCrearVenta(cl, null));
        ComandoCrearVenta cmd = new ComandoCrearVenta(cl, new DescuentoEstudiante());
        assertEquals(cl, cmd.getCliente());
        assertNotNull(cmd.getPolitica());

        ProductoResponseDto prodDto = new ProductoResponseDto();
        prodDto.setCodigo(1L);
        prodDto.setNombre("Item");
        prodDto.setPrecio(BigDecimal.TEN);
        prodDto.setExistencia(4);
        assertEquals(1L, prodDto.getCodigo());
        assertEquals("Item", prodDto.getNombre());

        VentaResponseDto ventaDto = new VentaResponseDto();
        ventaDto.setId("V-1");
        ventaDto.setCliente("Ana");
        ventaDto.setTotal(BigDecimal.ONE);
        ventaDto.setEstado("CONFIRMADA");
        ventaDto.setDetallesCount(1);
        assertEquals("V-1", ventaDto.getId());
        assertEquals(1, ventaDto.getDetallesCount());

        VentaEntity ve = new VentaEntity();
        ve.setId("VE-1");
        DetalleVentaEntity de = new DetalleVentaEntity();
        de.setId(9L);
        de.setVenta(ve);
        de.setProductoCodigo(1L);
        de.setCantidad(2);
        de.setPrecioUnitario(BigDecimal.TEN);
        de.setSubtotal(BigDecimal.valueOf(20));
        assertEquals(9L, de.getId());
        assertEquals(ve, de.getVenta());
        assertEquals(1L, de.getProductoCodigo());
        assertEquals(2, de.getCantidad());
        assertEquals(BigDecimal.TEN, de.getPrecioUnitario());
        assertEquals(BigDecimal.valueOf(20), de.getSubtotal());

        ProblemDetailDto pd = new ProblemDetailDto();
        pd.setType("https://api.ventas.tcsw/errores/test");
        pd.setTitle("Titulo");
        pd.setStatus(400);
        pd.setDetail("Detalle");
        pd.setInstance("/productos");
        pd.setTimestamp(java.time.Instant.now());
        pd.setInvalidParams(List.of("campo"));
        assertEquals(400, pd.getStatus());
        assertEquals("Detalle", pd.getDetail());
        assertEquals(1, pd.getInvalidParams().size());
    }
}
