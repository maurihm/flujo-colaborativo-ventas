package ventas.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ventas.adapters.in.rest.dto.ProductoRequestDto;
import ventas.adapters.in.rest.dto.ProductoResponseDto;
import ventas.application.ComandoRegistrarProducto;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.in.RegistrarProductoUseCase;
import ventas.ports.out.ProductoRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoRestController {

    private final RegistrarProductoUseCase registrarProductoUseCase;
    private final ProductoRepository productoRepository;

    public ProductoRestController(RegistrarProductoUseCase registrarProductoUseCase,
                                  ProductoRepository productoRepository) {
        this.registrarProductoUseCase = registrarProductoUseCase;
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> listarTodos() {
        List<ProductoResponseDto> productos = productoRepository.listarTodos().stream()
                .map(this::aDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProductoResponseDto> obtenerPorCodigo(@PathVariable Long codigo) {
        Producto producto = productoRepository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new NoSuchElementException("No existe el producto con codigo: " + codigo));
        return ResponseEntity.ok(aDto(producto));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> registrarProducto(@Valid @RequestBody ProductoRequestDto dto) {
        ComandoRegistrarProducto comando = new ComandoRegistrarProducto(
                dto.getCodigo(),
                dto.getNombre(),
                new Dinero(dto.getPrecio().doubleValue()),
                dto.getExistencia(),
                Categoria.GENERAL
        );

        Producto creado = registrarProductoUseCase.registrar(comando);
        ProductoResponseDto response = aDto(creado);
        URI ubicacion = URI.create("/productos/" + response.getCodigo());
        return ResponseEntity.created(ubicacion).body(response);
    }

    private ProductoResponseDto aDto(Producto p) {
        return new ProductoResponseDto(
                p.getCodigo(),
                p.getNombre(),
                BigDecimal.valueOf(p.getPrecio().getMonto()),
                p.getExistencia()
        );
    }
}
