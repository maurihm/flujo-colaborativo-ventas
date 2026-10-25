package ventas.adapters.out.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ProductoJdbcRepositoryTest {

    private ProductoJdbcRepository repository;
    private boolean dbDisponible = false;

    @BeforeEach
    void setUp() {
        repository = new ProductoJdbcRepository();
        try (Connection cn = ConexionBD.obtenerConexion();
             Statement st = cn.createStatement()) {
            dbDisponible = true;
            st.execute("CREATE TABLE IF NOT EXISTS producto (" +
                       "codigo BIGINT PRIMARY KEY, " +
                       "nombre VARCHAR(150) NOT NULL, " +
                       "precio NUMERIC(12, 2) NOT NULL CHECK (precio >= 0), " +
                       "existencia INTEGER NOT NULL CHECK (existencia >= 0), " +
                       "categoria VARCHAR(50) NOT NULL)");
            st.execute("DELETE FROM producto WHERE codigo IN (501, 502)");
        } catch (SQLException e) {
            dbDisponible = false;
        }
    }

    @Test
    void testGuardarYBuscarProductoExitoso() {
        assumeTrue(dbDisponible, "PostgreSQL no está disponible para pruebas de integración");

        Producto producto = new Producto(501L, "Monitor 4K", new Dinero(7500.00), 8, Categoria.ELECTRONICA);
        repository.guardar(producto);

        Optional<Producto> encontrado = repository.buscarPorCodigo(501L);
        assertTrue(encontrado.isPresent());
        assertEquals("Monitor 4K", encontrado.get().getNombre());
        assertEquals(7500.00, encontrado.get().getPrecio().getMonto());
        assertEquals(8, encontrado.get().getExistencia());
        assertEquals(Categoria.ELECTRONICA, encontrado.get().getCategoria());
    }

    @Test
    void testBuscarProductoInexistenteRetornaVacio() {
        assumeTrue(dbDisponible, "PostgreSQL no está disponible para pruebas de integración");

        Optional<Producto> resultado = repository.buscarPorCodigo(999999L);
        assertTrue(resultado.isEmpty());
    }
}
