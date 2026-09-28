package ventas.application;

import org.junit.jupiter.api.Test;
import ventas.adapters.out.memory.InMemoryVentaRepository;
import ventas.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VentaFacadeTest {

    @Test
    void procesarVentaCompletaDebeCrearGuardarYConfirmar() {
        // Arrange: Preparar la infraestructura
        InMemoryVentaRepository repo = new InMemoryVentaRepository();
        ConfirmarVentaService confirmarService = new ConfirmarVentaService(repo);
        VentaFacade facade = new VentaFacade(repo, confirmarService);

        // Datos de prueba
        Cliente cliente = new Cliente(1L, "Axel", "Garcia", "555-0000");
        Producto producto = new Producto(1L, "Teclado", new Dinero(500.0), 10, Categoria.ELECTRONICA);

        // Act: Ejecutar la fachada (sin el argumento de politica de descuento)
        Venta ventaProcesada = facade.procesarVentaCompleta(
                cliente, 
                List.of(producto), 
                List.of(2)
        );

        // Assert: Verificaciones
        assertNotNull(ventaProcesada, "La venta no debe ser nula");
        assertEquals(cliente, ventaProcesada.getCliente(), "El cliente debe asignarse correctamente");
        assertEquals(1, ventaProcesada.getDetalles().size(), "Debe tener exactamente 1 detalle de venta");
        
        // Verificar que la confirmación funcionó
        assertEquals(EstadoVenta.PAGADA, ventaProcesada.getEstado(), "El estado de la venta debe ser PAGADA tras confirmar");
    }
}