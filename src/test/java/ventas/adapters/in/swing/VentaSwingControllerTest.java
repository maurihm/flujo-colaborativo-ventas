package ventas.adapters.in.swing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ventas.adapters.out.memory.InMemoryProductoRepository;
import ventas.adapters.out.memory.InMemoryVentaRepository;
import ventas.application.ConfirmarVentaService;
import ventas.application.RegistrarProductoService;
import ventas.application.VentaFacade;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.out.ProductoRepository;
import ventas.ports.out.VentaRepository;

import javax.swing.*;
import java.awt.Component;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class VentaSwingControllerTest {

    private ProductoRepository productoRepo;
    private VentaRepository ventaRepo;
    private RegistrarProductoService registrarService;
    private VentaFacade ventaFacade;
    private TestableVentaSwingController controller;
    private ProductoTableModel tableModel;

    static class TestableVentaSwingController extends VentaSwingController {
        String ultimoMensaje;
        String ultimoTitulo;
        int ultimoTipo;
        CountDownLatch latch;

        public TestableVentaSwingController(RegistrarProductoService registrarService, VentaFacade ventaFacade) {
            super(registrarService, ventaFacade);
        }

        @Override
        protected void mostrarMensaje(Component parent, String mensaje, String titulo, int tipo) {
            this.ultimoMensaje = mensaje;
            this.ultimoTitulo = titulo;
            this.ultimoTipo = tipo;
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    @BeforeEach
    void setUp() {
        productoRepo = new InMemoryProductoRepository();
        ventaRepo = new InMemoryVentaRepository();
        registrarService = new RegistrarProductoService(productoRepo);
        ConfirmarVentaService confirmarService = new ConfirmarVentaService(ventaRepo);
        ventaFacade = new VentaFacade(ventaRepo, confirmarService);
        controller = new TestableVentaSwingController(registrarService, ventaFacade);
        tableModel = new ProductoTableModel();
    }

    @Test
    void testRegistrarProductoConCamposVaciosMuestraAdvertenciaYNoRegistra() {
        FormularioProductoPanel form = new FormularioProductoPanel();
        controller.registrarProductoAsync(form, tableModel, null);

        assertEquals("Validación Fallida", controller.ultimoTitulo);
        assertEquals(0, tableModel.getRowCount());
        assertTrue(productoRepo.listarTodos().isEmpty());
    }

    @Test
    void testProcesarVentaConCarritoVacioMuestraAdvertencia() {
        CarritoPanel carrito = new CarritoPanel();
        controller.procesarVentaAsync(carrito, null);

        assertEquals("Atención", controller.ultimoTitulo);
        assertEquals("El carrito está vacío.", controller.ultimoMensaje);
    }

    @Test
    void testRegistrarProductoValidoActualizaModeloYPersiste() throws Exception {
        FormularioProductoPanel form = new FormularioProductoPanel();
        setCampoTexto(form, "txtCodigo", "501");
        setCampoTexto(form, "txtNombre", "Teclado Inalambrico");
        setCampoTexto(form, "txtPrecio", "750.00");
        setCampoTexto(form, "txtStock", "10");

        controller.latch = new CountDownLatch(1);
        controller.registrarProductoAsync(form, tableModel, null);

        assertTrue(controller.latch.await(3, TimeUnit.SECONDS));
        assertEquals("Éxito", controller.ultimoTitulo);
        assertEquals(1, tableModel.getRowCount());
        assertTrue(productoRepo.buscarPorCodigo(501L).isPresent());
    }

    @Test
    void testProcesarVentaExitosaVaciaCarritoYConfirma() throws Exception {
        Producto prod = new Producto(100L, "Cable HDMI", new Dinero(120.00), 20, Categoria.ELECTRONICA);
        productoRepo.guardar(prod);

        CarritoPanel carrito = new CarritoPanel();
        carrito.agregarItem(prod, 2);

        controller.latch = new CountDownLatch(1);
        controller.procesarVentaAsync(carrito, null);

        assertTrue(controller.latch.await(3, TimeUnit.SECONDS));
        assertEquals("Venta Exitosa", controller.ultimoTitulo);
        assertEquals(0, carrito.getItems().size());
    }

    private void setCampoTexto(FormularioProductoPanel form, String nombreCampo, String valor) throws Exception {
        Field field = FormularioProductoPanel.class.getDeclaredField(nombreCampo);
        field.setAccessible(true);
        JTextField txt = (JTextField) field.get(form);
        txt.setText(valor);
    }
}
