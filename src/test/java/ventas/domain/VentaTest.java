package ventas.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VentaTest {

    @Test
    void agregaDetalleValidoYCalculaSubtotal() {
        Producto producto = new Producto(1L, "Refresco", new Dinero(15.00), 10);
        Venta venta = new Venta();

        venta.agregarDetalle(producto, 3);

        assertEquals(new Dinero(45.00), venta.calcularTotal());
    }

    @Test
    void rechazaCantidadCeroOMenorAlAgregar() {
        Producto producto = new Producto(1L, "Refresco", new Dinero(15.00), 10);
        Venta venta = new Venta();

        assertThrows(IllegalArgumentException.class, () -> venta.agregarDetalle(producto, 0));
        assertThrows(IllegalArgumentException.class, () -> venta.agregarDetalle(producto, -2));
    }

    @Test
    void calculaTotalComoSumaDeSubtotales() {
        Producto refresco = new Producto(1L, "Refresco", new Dinero(15.00), 10);
        Producto papas = new Producto(2L, "Papas", new Dinero(20.00), 5);
        Venta venta = new Venta();

        venta.agregarDetalle(refresco, 2);
        venta.agregarDetalle(papas, 1);

        assertEquals(new Dinero(50.00), venta.calcularTotal());
    }

    @Test
    void precioCapturadoNoCambiaSiElProductoCambiaDespues() {
        Producto producto = new Producto(1L, "Refresco", new Dinero(15.00), 10);
        Venta venta = new Venta();

        venta.agregarDetalle(producto, 2);

        producto.actualizarPrecio(new Dinero(25.00));

        assertEquals(new Dinero(30.00), venta.calcularTotal());
    }

    @Test
    void noPermiteModificarLaListaDeDetallesDirectamente() {
        Producto producto = new Producto(1L, "Refresco", new Dinero(15.00), 10);
        Venta venta = new Venta();
        venta.agregarDetalle(producto, 1);

        List<DetalleVenta> detalles = venta.getDetalles();
        DetalleVenta detalleExterno = new DetalleVenta(producto, 1);

        assertThrows(UnsupportedOperationException.class, () -> detalles.add(detalleExterno));
    }

    @Test
    void asignaClienteValidoYLoObtiene() {
        Cliente cliente = new Cliente(1L, "Juan", "Perez", null);
        Venta venta = new Venta();

        venta.asignarCliente(cliente);

        assertEquals(cliente, venta.getCliente());
    }

    @Test
    void rechazaAsignarClienteNulo() {
        Venta venta = new Venta();

        assertThrows(IllegalArgumentException.class, () -> venta.asignarCliente(null));
    }
}
