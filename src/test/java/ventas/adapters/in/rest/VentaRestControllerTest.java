package ventas.adapters.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ventas.adapters.in.rest.dto.CrearVentaRequestDto;
import ventas.adapters.in.rest.dto.ItemVentaDto;
import ventas.adapters.out.memory.InMemoryProductoRepository;
import ventas.adapters.out.memory.InMemoryVentaRepository;
import ventas.application.ConfirmarVentaService;
import ventas.application.VentaFacade;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VentaRestControllerTest {

    private MockMvc mockMvc;
    private InMemoryProductoRepository productoRepo;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        productoRepo = new InMemoryProductoRepository();
        InMemoryVentaRepository ventaRepo = new InMemoryVentaRepository();
        ConfirmarVentaService confirmarService = new ConfirmarVentaService(ventaRepo);
        VentaFacade facade = new VentaFacade(ventaRepo, confirmarService);
        VentaRestController controller = new VentaRestController(facade, productoRepo);
        ApiExceptionHandler exceptionHandler = new ApiExceptionHandler();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionHandler)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("POST /ventas procesa exitosamente la venta y retorna 201")
    void testProcesarVentaExitosa() throws Exception {
        productoRepo.guardar(new Producto(10L, "Cable HDMI", new Dinero(150.0), 10, Categoria.GENERAL));

        CrearVentaRequestDto dto = new CrearVentaRequestDto(
                "Carlos Mendoza",
                "carlos@uv.mx",
                List.of(new ItemVentaDto(10L, 2))
        );

        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cliente", is("Carlos Mendoza")))
                .andExpect(jsonPath("$.total", is(300.0)))
                .andExpect(jsonPath("$.estado", is("CONFIRMADA")))
                .andExpect(jsonPath("$.detallesCount", is(1)));
    }

    @Test
    @DisplayName("POST /ventas con producto inexistente retorna 404 Problem Details")
    void testProcesarVentaProductoInexistente() throws Exception {
        CrearVentaRequestDto dto = new CrearVentaRequestDto(
                "Carlos Mendoza",
                "carlos@uv.mx",
                List.of(new ItemVentaDto(9999L, 1))
        );

        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is("Recurso no encontrado")));
    }

    @Test
    @DisplayName("POST /ventas sin items retorna 400 Bad Request por validacion")
    void testProcesarVentaSinItemsRetorna400() throws Exception {
        CrearVentaRequestDto dto = new CrearVentaRequestDto(
                "Carlos Mendoza",
                "carlos@uv.mx",
                Collections.emptyList()
        );

        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is("Entrada invalida")));
    }
}
