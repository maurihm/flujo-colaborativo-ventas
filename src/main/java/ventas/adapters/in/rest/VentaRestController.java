package ventas.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ventas.adapters.in.rest.dto.CrearVentaRequestDto;
import ventas.adapters.in.rest.dto.ItemVentaDto;
import ventas.adapters.in.rest.dto.VentaResponseDto;
import ventas.application.VentaFacade;
import ventas.domain.Cliente;
import ventas.domain.EstadoVenta;
import ventas.domain.Producto;
import ventas.domain.Venta;
import ventas.ports.out.ProductoRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/ventas")
public class VentaRestController {

    private final VentaFacade ventaFacade;
    private final ProductoRepository productoRepository;

    public VentaRestController(VentaFacade ventaFacade, ProductoRepository productoRepository) {
        this.ventaFacade = ventaFacade;
        this.productoRepository = productoRepository;
    }

    @PostMapping
    public ResponseEntity<VentaResponseDto> procesarVenta(@Valid @RequestBody CrearVentaRequestDto dto) {
        Cliente cliente = new Cliente(1L, dto.getClienteNombre(), dto.getClienteEmail(), dto.getClienteEmail());

        List<Producto> productos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        for (ItemVentaDto item : dto.getItems()) {
            Producto p = productoRepository.buscarPorCodigo(item.getCodigoProducto())
                    .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + item.getCodigoProducto()));
            productos.add(p);
            cantidades.add(item.getCantidad());
        }

        Venta venta = ventaFacade.procesarVentaCompleta(cliente, productos, cantidades);

        String estado = venta.getEstado() == EstadoVenta.PAGADA
                ? "CONFIRMADA"
                : venta.getEstado().name();

        VentaResponseDto response = new VentaResponseDto(
                venta.getId(),
                venta.getCliente().getNombre(),
                BigDecimal.valueOf(venta.calcularSubtotal().getMonto()),
                estado,
                venta.getDetalles().size()
        );

        URI uri = URI.create("/ventas/" + response.getId());
        return ResponseEntity.created(uri).body(response);
    }
}
