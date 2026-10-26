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

class TransaccionVentaExitosaTest {

    private VentaJdbcRepository ventaRepo;
    private ProductoJdbcRepository productoRepo;

    @BeforeEach
    void setup() {
        ventaRepo = new VentaJdbcRepository();
        productoRepo = new ProductoJdbcRepository();
        try (Connection cn = ConexionBD.obtenerConexion();
             Statement st = cn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS producto (codigo BIGINT PRIMARY KEY, nombre VARCHAR(150) NOT NULL, precio NUMERIC(12,2) NOT NULL, existencia INTEGER NOT NULL, categoria VARCHAR(50) NOT NULL)");
            st.execute("CREATE TABLE IF NOT EXISTS venta (id VARCHAR(50) PRIMARY KEY, cliente_codigo BIGINT NOT NULL, cliente_nombre VARCHAR(100) NOT NULL, cliente_apellido VARCHAR(100) NOT NULL, cliente_telefono VARCHAR(20) NOT NULL, total NUMERIC(12,2) NOT NULL, estado VARCHAR(30) NOT NULL, fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            st.execute("CREATE TABLE IF NOT EXISTS detalle_venta (id SERIAL PRIMARY KEY, venta_id VARCHAR(50) NOT NULL REFERENCES venta(id) ON DELETE CASCADE, producto_codigo BIGINT NOT NULL REFERENCES producto(codigo), cantidad INTEGER NOT NULL, precio_unitario NUMERIC(12,2) NOT NULL, subtotal NUMERIC(12,2) NOT NULL)");

            st.execute("DELETE FROM detalle_venta WHERE venta_id = 'V-TX-001'");
            st.execute("DELETE FROM venta WHERE id = 'V-TX-001'");
            st.execute("DELETE FROM producto WHERE codigo = 601");
        } catch (SQLException ignored) {
            // Se ignora si las tablas aun no contienen registros previos
        }
    }

    @Test
    void testTransaccionVentaExitosaDescuentaStockYPersisteDetalles() throws SQLException {
        Producto p = new Producto(601L, "Teclado Ergonomico", new Dinero(1200.00), 10, Categoria.ELECTRONICA);
        productoRepo.guardar(p);

        Venta venta = new Venta("V-TX-001");
        venta.asignarCliente(new Cliente(1L, "Mauricio", "Hernandez", "2289998877"));
        venta.agregarDetalle(p, 3); // Compra 3 unidades

        ventaRepo.guardar(venta);

        // Verificar que el stock se redujo a 7
        Producto pActualizado = productoRepo.buscarPorCodigo(601L).orElseThrow();
        assertEquals(7, pActualizado.getExistencia());

        // Verificar detalle persistido
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM detalle_venta WHERE venta_id = ?")) {
            ps.setString(1, "V-TX-001");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(1, rs.getInt(1));
            }
        }
    }
}
