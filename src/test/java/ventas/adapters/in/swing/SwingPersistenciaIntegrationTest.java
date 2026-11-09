package ventas.adapters.in.swing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ventas.adapters.out.hibernate.HibernateUtil;
import ventas.adapters.out.hibernate.ProductoHibernateRepository;
import ventas.adapters.out.jdbc.ConexionBD;
import ventas.application.ComandoRegistrarProducto;
import ventas.application.RegistrarProductoService;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class SwingPersistenciaIntegrationTest {

    private ProductoHibernateRepository productoRepo;
    private RegistrarProductoService registrarService;
    private ProductoTableModel tableModel;
    private boolean dbDisponible = false;

    @BeforeEach
    void setUp() {
        try (Connection cn = ConexionBD.obtenerConexion();
             Statement st = cn.createStatement()) {
            dbDisponible = true;
            st.execute("CREATE TABLE IF NOT EXISTS producto (" +
                       "codigo BIGINT PRIMARY KEY, " +
                       "nombre VARCHAR(150) NOT NULL, " +
                       "precio NUMERIC(12, 2) NOT NULL CHECK (precio >= 0), " +
                       "existencia INTEGER NOT NULL CHECK (existencia >= 0), " +
                       "categoria VARCHAR(50) NOT NULL)");
            st.execute("DELETE FROM producto WHERE codigo IN (701, 702)");
        } catch (SQLException e) {
            dbDisponible = false;
        }

        productoRepo = new ProductoHibernateRepository();
        registrarService = new RegistrarProductoService(productoRepo);
        tableModel = new ProductoTableModel();
    }

    @Test
    void testRegistroDesdeServicioPersisteEnPostgresConHibernateYReflejaEnTableModel() {
        assumeTrue(dbDisponible && HibernateUtil.getSessionFactory() != null, "PostgreSQL o Hibernate no disponible");

        ComandoRegistrarProducto cmd = new ComandoRegistrarProducto(
                701L, "Monitor Gamer 144Hz", new Dinero(4500.00), 10, Categoria.ELECTRONICA
        );
        Producto registrado = registrarService.registrar(cmd);
        tableModel.agregarProducto(registrado);

        Optional<Producto> enBD = productoRepo.buscarPorCodigo(701L);
        assertTrue(enBD.isPresent(), "El producto debe existir en PostgreSQL tras registrarse con Hibernate");
        assertEquals("Monitor Gamer 144Hz", enBD.get().getNombre());
        assertEquals(4500.00, enBD.get().getPrecio().getMonto());

        List<Producto> catalogo = productoRepo.listarTodos();
        assertTrue(catalogo.stream().anyMatch(p -> p.getCodigo() == 701L), "listarTodos debe contener el producto");

        assertEquals(1, tableModel.getRowCount());
        assertEquals(701L, tableModel.getValueAt(0, 0));
        assertEquals("Monitor Gamer 144Hz", tableModel.getValueAt(0, 1));
    }
}
