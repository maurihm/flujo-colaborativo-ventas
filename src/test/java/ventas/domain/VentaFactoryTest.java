package ventas.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VentaFactoryTest {

    @Test
    void debeCrearVentaConClienteVálido() {
        Cliente cliente = new Cliente(1L, "123", "Juan Perez", "juan@test.com");
        
        Venta venta = VentaFactory.crear(cliente);
        
        assertNotNull(venta);
        assertEquals(cliente, venta.getCliente());
        assertEquals(EstadoVenta.NUEVA, venta.getEstado());
        assertNull(venta.getId(), "El ID debe ser nulo hasta que se guarde en BD");
    }

    @Test
    void debeLanzarExcepcionSiClienteEsNulo() {
        Cliente clienteNulo = null;
        
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class, 
            () -> VentaFactory.crear(clienteNulo)
        );
        assertEquals("El cliente es obligatorio para crear una venta", excepcion.getMessage());
    }

    @Test
    void debeCrearDetalleVentaValido() {
        Producto producto = new Producto(1L, "Laptop", new Dinero(1000.0), 10);
        int cantidad = 2;
        
        DetalleVenta detalle = DetalleVentaFactory.crear(producto, cantidad);
        
        assertNotNull(detalle);
        assertEquals(producto, detalle.getProducto());
        assertEquals(cantidad, detalle.getCantidad());
    }

    @Test
    void debeLanzarExcepcionSiProductoEnDetalleEsNulo() {
        Producto productoNulo = null;
        
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class, 
            () -> DetalleVentaFactory.crear(productoNulo, 1)
        );
        assertEquals("El producto es obligatorio para el detalle de venta", excepcion.getMessage());
    }

    @Test
    void debeLanzarExcepcionSiCantidadEnDetalleEsInvalida() {
        Producto producto = new Producto(1L, "Laptop", new Dinero(1000.0), 10);
        
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class, 
            () -> DetalleVentaFactory.crear(producto, 0)
        );
        assertEquals("La cantidad debe ser mayor a cero", excepcion.getMessage());
    }
}
