package ventas.adapters.out.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ventas.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class TransaccionRollbackTest {
    private VentaJdbcRepository ventaRepo;
    private ProductoJdbcRepository productoRepo;
    
    // FIX SONARQUBE: Constante para evitar duplicar el texto "V-FAIL-001"
    private static final String VENTA_FAIL_ID = "V-FAIL-001";

    @BeforeEach
    void setup() {
        ventaRepo = new VentaJdbcRepository();
        productoRepo = new ProductoJdbcRepository();

        try (Connection cn = ConexionBD.obtenerConexion();
             Statement st = cn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS producto (codigo BIGINT PRIMARY KEY, nombre VARCHAR(150) NOT NULL, precio NUMERIC(12,2) NOT NULL, existencia INTEGER NOT NULL, categoria VARCHAR(50) NOT NULL)");
            st.execute("CREATE TABLE IF NOT EXISTS venta (id VARCHAR(50) PRIMARY KEY, cliente_codigo BIGINT NOT NULL, cliente_nombre VARCHAR(100) NOT NULL, cliente_apellido VARCHAR(100) NOT NULL, cliente_telefono VARCHAR(20) NOT NULL, total NUMERIC(12,2) NOT NULL, estado VARCHAR(30) NOT NULL, fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            st.execute("CREATE TABLE IF NOT EXISTS detalle_venta (id SERIAL PRIMARY KEY, venta_id VARCHAR(50) NOT NULL REFERENCES venta(id) ON DELETE CASCADE, producto_codigo BIGINT NOT NULL REFERENCES producto (codigo), cantidad INTEGER NOT NULL, precio_unitario NUMERIC (12,2) NOT NULL, subtotal NUMERIC(12,2) NOT NULL)");

            st.execute("DELETE FROM detalle_venta WHERE venta_id = '" + VENTA_FAIL_ID + "'");
            st.execute("DELETE FROM venta WHERE id = '" + VENTA_FAIL_ID + "'");
            st.execute("DELETE FROM producto WHERE codigo IN (701, 702)");
        } catch (SQLException ignored) {
            /* FIX SONARQUBE: ignorado intencionalmente para la limpieza inicial */
        }
    }

    @Test
    void testFalloEnDetalleEjecutaRollbackYNoDejaEstadoParcial() throws SQLException {
        // Producto 1 con suficiente stock
        Producto p1 = new Producto(701L, "Laptop de Prueba", new Dinero(15000.00), 5, Categoria.ELECTRONICA);
        productoRepo.guardar(p1);

        // Producto 2 que NO existe en la base de datos (forzará violación de clave foránea)
        Producto p2Inexistente = new Producto(702L, "Producto Fantasma", new Dinero(500.00), 2, Categoria.GENERAL);

        Venta venta = new Venta(VENTA_FAIL_ID);
        venta.asignarCliente(new Cliente(2L, "Axel", "Garcia", "2287776655"));
        venta.agregarDetalle(p1, 2);
        venta.agregarDetalle(p2Inexistente, 1); // Violación de FK al insertar en detalle_venta

        // La ejecución debe fallar y propagar excepción
        RuntimeException ex = assertThrows(RuntimeException.class, () -> ventaRepo.guardar(venta));
        assertTrue(ex.getMessage().contains("Transacción de venta fallida"));

        // COMPROBACIONES DE ATOMICIDAD (M11):
        // 1. Que NO exista el encabezado de venta
        assertNull(ventaRepo.buscarPorId(VENTA_FAIL_ID));

        // 2. Que NO existan detalles huérfanos
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM detalle_venta WHERE venta_id = ?")) {
            ps.setString(1, VENTA_FAIL_ID);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(0, rs.getInt(1), "No deben existir detalles de venta huérfanos tras rollback");
            }
        }

        // 3. Que el producto p1 conserve su existencia inicial intacta (5 unidades)
        Producto p1Recuperado = productoRepo.buscarPorCodigo(701L).orElseThrow();
        assertEquals(5, p1Recuperado.getExistencia(), "El stock del producto no debió ser descontado");
    }
}