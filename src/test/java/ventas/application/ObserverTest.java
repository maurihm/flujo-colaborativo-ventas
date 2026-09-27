package ventas.application;

import org.junit.jupiter.api.Test;
import ventas.domain.Categoria;
import ventas.domain.Cliente;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.domain.Venta;
import ventas.domain.VentaConfirmada;
import ventas.domain.EstadoVenta;
import ventas.adapters.out.memory.InMemoryVentaRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ObserverTest {

    @Test
    void notificarConfirmacionDescuentaInventario() {
        // Arrange
        Producto producto = new Producto(1L, "Laptop", new Dinero(1000.0), 10, Categoria.ELECTRONICA);
        Venta venta = new Venta();
        venta.asignarCliente(new Cliente(1L, "Juan", "Perez", "555-1234"));
        venta.agregarDetalle(producto, 2);
        
        InMemoryVentaRepository repo = new InMemoryVentaRepository();
        repo.guardar(venta);
        
        ConfirmarVentaService service = new ConfirmarVentaService(repo);
        ActualizadorInventario actualizador = new ActualizadorInventario();
        BitacoraVenta bitacora = new BitacoraVenta();
        
        service.agregarObservador(actualizador);
        service.agregarObservador(bitacora);
        
        // Act
        service.confirmar(venta.getId());
        
        // Assert
        assertEquals(8, producto.getExistencia());
        assertEquals(EstadoVenta.PAGADA, repo.buscarPorId(venta.getId()).getEstado());
    }
}
