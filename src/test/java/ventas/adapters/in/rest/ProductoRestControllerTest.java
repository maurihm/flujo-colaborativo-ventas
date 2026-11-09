package ventas.adapters.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ventas.adapters.in.rest.dto.ProductoRequestDto;
import ventas.adapters.out.memory.InMemoryProductoRepository;
import ventas.application.RegistrarProductoService;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.in.RegistrarProductoUseCase;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductoRestControllerTest {

    private MockMvc mockMvc;
    private InMemoryProductoRepository repository;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository = new InMemoryProductoRepository();
        RegistrarProductoUseCase useCase = new RegistrarProductoService(repository);
        ProductoRestController controller = new ProductoRestController(useCase, repository);
        ApiExceptionHandler exceptionHandler = new ApiExceptionHandler();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionHandler)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /productos debe retornar 200 y la lista de productos")
    void testListarProductosExitoso() throws Exception {
        repository.guardar(new Producto(101L, "Teclado", new Dinero(500.0), 10, Categoria.GENERAL));
        repository.guardar(new Producto(102L, "Mouse", new Dinero(250.0), 20, Categoria.GENERAL));

        mockMvc.perform(get("/productos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].codigo", is(101)))
                .andExpect(jsonPath("$[1].codigo", is(102)));
    }

    @Test
    @DisplayName("GET /productos/{codigo} existente retorna 200 y representacion DTO")
    void testObtenerProductoExistente() throws Exception {
        repository.guardar(new Producto(200L, "Monitor", new Dinero(3500.0), 5, Categoria.GENERAL));

        mockMvc.perform(get("/productos/200")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is(200)))
                .andExpect(jsonPath("$.nombre", is("Monitor")))
                .andExpect(jsonPath("$.precio", is(3500.0)))
                .andExpect(jsonPath("$.existencia", is(5)));
    }

    @Test
    @DisplayName("GET /productos/{codigo} inexistente retorna 404 con Problem Details RFC 9457")
    void testObtenerProductoInexistente() throws Exception {
        mockMvc.perform(get("/productos/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is("Recurso no encontrado")))
                .andExpect(jsonPath("$.detail", is("No existe el producto con codigo: 999")))
                .andExpect(jsonPath("$.instance", is("/productos/999")));
    }

    @Test
    @DisplayName("POST /productos con datos validos retorna 201 y encabezado Location")
    void testRegistrarProductoExitoso() throws Exception {
        ProductoRequestDto dto = new ProductoRequestDto(300L, "Impresora", new BigDecimal("2100.50"), 8);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/productos/300"))
                .andExpect(jsonPath("$.codigo", is(300)))
                .andExpect(jsonPath("$.nombre", is("Impresora")))
                .andExpect(jsonPath("$.precio", is(2100.50)))
                .andExpect(jsonPath("$.existencia", is(8)));
    }

    @Test
    @DisplayName("POST /productos con campos invalidos retorna 400 y detalle de validacion")
    void testRegistrarProductoInvalidoRetorna400() throws Exception {
        ProductoRequestDto dtoInvalido = new ProductoRequestDto(null, "", new BigDecimal("-10.00"), -1);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.title", is("Entrada invalida")))
                .andExpect(jsonPath("$.invalidParams", hasItem("El codigo es obligatorio")))
                .andExpect(jsonPath("$.invalidParams", hasItem("El nombre no puede estar vacio")))
                .andExpect(jsonPath("$.invalidParams", hasItem("El precio debe ser mayor a cero")))
                .andExpect(jsonPath("$.invalidParams", hasItem("La existencia no puede ser negativa")));
    }
}
