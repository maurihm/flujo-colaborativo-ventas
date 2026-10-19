package ventas.adapters.in.swing;

import org.junit.jupiter.api.Test;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductoTableModelTest {

    @Test
    void testModeloInicialVacio() {
        ProductoTableModel model = new ProductoTableModel();
        assertEquals(0, model.getRowCount());
        assertEquals(5, model.getColumnCount());
        assertEquals("Código", model.getColumnName(0));
        assertEquals("Nombre", model.getColumnName(1));
        assertEquals("Precio", model.getColumnName(2));
        assertEquals("Stock", model.getColumnName(3));
        assertEquals("Categoría", model.getColumnName(4));
    }

    @Test
    void testAgregarProductoYObtenerValores() {
        ProductoTableModel model = new ProductoTableModel();
        Producto p = new Producto(101L, "Teclado Mecánico", new Dinero(850.00), 10, Categoria.ELECTRONICA);
        model.agregarProducto(p);

        assertEquals(1, model.getRowCount());
        assertEquals(101L, model.getValueAt(0, 0));
        assertEquals("Teclado Mecánico", model.getValueAt(0, 1));
        assertEquals(String.format("$%.2f", 850.00), model.getValueAt(0, 2));
        assertEquals(10, model.getValueAt(0, 3));
        assertEquals(Categoria.ELECTRONICA, model.getValueAt(0, 4));
        assertEquals(p, model.getProductoAt(0));
    }

    @Test
    void testIndicesFueraDeRango() {
        ProductoTableModel model = new ProductoTableModel();
        assertNull(model.getValueAt(-1, 0));
        assertNull(model.getValueAt(5, 0));
        assertNull(model.getProductoAt(-1));
        assertNull(model.getProductoAt(2));
    }

    @Test
    void testSetProductos() {
        ProductoTableModel model = new ProductoTableModel();
        Producto p1 = new Producto(1L, "Mouse", new Dinero(200.00), 5, Categoria.ELECTRONICA);
        Producto p2 = new Producto(2L, "Monitor", new Dinero(3500.00), 2, Categoria.ELECTRONICA);

        model.setProductos(List.of(p1, p2));
        assertEquals(2, model.getRowCount());
        assertEquals(p1, model.getProductoAt(0));
        assertEquals(p2, model.getProductoAt(1));
    }
}
