package ventas.adapters.out.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ventas.domain.Cliente;
import ventas.domain.EstadoVenta;
import ventas.domain.Venta;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class VentaJdbcRepositoryTest {
    private VentaJdbcRepository repository;

    @BeforeEach
    void setup() {
        repository = new VentaJdbcRepository();
        try (Connection cn = ConexionBD.obtenerConexion();
             Statement st = cn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS venta (" +
                       "id VARCHAR(50) PRIMARY KEY, " +
                       "cliente_codigo BIGINT NOT NULL, " +
                       "cliente_nombre VARCHAR(100) NOT NULL, " +
                       "cliente_apellido VARCHAR(100) NOT NULL, " +
                       "cliente_telefono VARCHAR(20) NOT NULL, " +
                       "total NUMERIC(12, 2) NOT NULL CHECK (total >= 0), " +
                       "estado VARCHAR(30) NOT NULL, " +
                       "fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");
            st.execute("DELETE FROM venta WHERE id = 'V-TEST-001'");
        } catch (SQLException ignored) {}
    }

    @Test
    void testGuardarYBuscarVentaExitoso() {
        Venta venta = new Venta("V-TEST-001");
        Cliente cliente = new Cliente(101L, "Valeria", "Hernandez", "2281002030");
        venta.asignarCliente(cliente);
        venta.setEstado(EstadoVenta.NUEVA);

        repository.guardar(venta);

        Venta recuperada = repository.buscarPorId("V-TEST-001");
        assertNotNull(recuperada);
        assertEquals("V-TEST-001", recuperada.getId());
        assertEquals("Valeria", recuperada.getCliente().getNombre());
        assertEquals(EstadoVenta.NUEVA, recuperada.getEstado());
    }

    @Test
    void testBuscarVentaInexistenteRetornaNull() {
        Venta inexistente = repository.buscarPorId("V-NO-EXISTE");
        assertNull(inexistente);
    }
}
