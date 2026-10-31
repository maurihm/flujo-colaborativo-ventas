package ventas.adapters.out.hibernate;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ventas.domain.Categoria;
import ventas.domain.Cliente;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.domain.Venta;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TransaccionHibernateVentaTest {

    private ProductoHibernateRepository productoRepo;
    private VentaHibernateRepository ventaRepo;
    private boolean hibernateDisponible = false;

    @BeforeEach
    void setUp() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if (sf != null && !sf.isClosed()) {
            hibernateDisponible = true;
            productoRepo = new ProductoHibernateRepository(sf);
            ventaRepo = new VentaHibernateRepository(sf);
        }
    }

    @Test
    void testTransaccionVentaExitosaPersisteVentaYDescuentaStock() {
        assumeTrue(hibernateDisponible, "Hibernate no disponible para prueba de integración");

        // 1. Preparar producto con 15 piezas de inventario
        Producto p = new Producto(801L, "Disco SSD 1TB NVMe", new Dinero(1500.00), 15, Categoria.ELECTRONICA);
        productoRepo.guardar(p);

        // 2. Crear y persistir venta de 3 piezas
        String idVenta = "V-HIB-" + System.nanoTime();
        Cliente cliente = new Cliente(10L, "Carlos", "Mendoza", "2281002233");
        Venta venta = new Venta(idVenta);
        venta.asignarCliente(cliente);
        venta.agregarDetalle(p, 3);

        ventaRepo.guardar(venta);

        // 3. Verificar que la venta fue guardada
        Venta recuperada = ventaRepo.buscarPorId(idVenta);
        assertNotNull(recuperada);
        assertEquals(idVenta, recuperada.getId());
        assertEquals("Carlos", recuperada.getCliente().getNombre());

        // 4. Verificar que el inventario disminuyó exactamente a 12
        Optional<Producto> prodActualizado = productoRepo.buscarPorCodigo(801L);
        assertTrue(prodActualizado.isPresent());
        assertEquals(12, prodActualizado.get().getExistencia());
    }
}
