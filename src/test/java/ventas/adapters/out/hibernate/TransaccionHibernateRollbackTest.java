package ventas.adapters.out.hibernate;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ventas.domain.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TransaccionHibernateRollbackTest {

    private ProductoHibernateRepository productoRepo;
    private VentaHibernateRepository ventaRepo;
    private boolean hibernateDisponible = false;

    @BeforeEach
    void setup() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if (sf != null && !sf.isClosed()) {
            hibernateDisponible = true;
            productoRepo = new ProductoHibernateRepository(sf);
            ventaRepo = new VentaHibernateRepository(sf);
        }
    }

    @Test
    void testFalloEnDetalleEjecutaRollbackYSinEstadoResidual() {
        assumeTrue(hibernateDisponible, "Hibernate no disponible para prueba de integración");

        // 1. Producto existente con 20 piezas
        Producto p1 = new Producto(802L, "Audifonos Bluetooth", new Dinero(850.00), 20, Categoria.ELECTRONICA);
        productoRepo.guardar(p1);

        // 2. Producto con código no existente en BD para forzar violación de clave foránea / fallo
        Producto pInexistente = new Producto(999999L, "Fantasma", new Dinero(100.00), 5, Categoria.GENERAL);

        // 3. Crear venta con ID dinámico para evitar conflictos en la caché de pruebas
        String idVenta = "V-FAIL-" + System.nanoTime();
        Cliente cliente = new Cliente(11L, "Lucia", "Ramos", "2288112233");
        Venta venta = new Venta(idVenta);
        venta.asignarCliente(cliente);
        venta.agregarDetalle(p1, 2);
        venta.agregarDetalle(pInexistente, 1);

        // 4. Se espera que lance RuntimeException por el fallo
        assertThrows(RuntimeException.class, () -> ventaRepo.guardar(venta));

        // 5. COMPROBACIÓN CENTRAL DE ROLLBACK:
        Venta ventaEnBD = ventaRepo.buscarPorId(idVenta);
        assertNull(ventaEnBD, "La venta no debe existir en la base de datos debido al rollback");

        Optional<Producto> prodDespues = productoRepo.buscarPorCodigo(802L);
        assertTrue(prodDespues.isPresent());
        assertEquals(20, prodDespues.get().getExistencia(), "El stock debe permanecer intacto tras el rollback");
    }
}