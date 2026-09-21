package ventas.application;

import org.junit.jupiter.api.Test;
import ventas.adapters.out.memory.InMemoryVentaRepository;
import ventas.domain.EstadoVenta;
import ventas.domain.Venta;
import ventas.ports.out.VentaRepository;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmarVentaServiceTest {

    @Test
    void debeConfirmarVentaExitosamente() {
        // Arrange (Preparar)
        // Declaramos usando la interfaz, tal como lo espera el constructor del servicio
        VentaRepository repository = new InMemoryVentaRepository();
        ConfirmarVentaService service = new ConfirmarVentaService(repository);
        
        Venta venta = new Venta("V-123"); // Por defecto nace con estado NUEVA
        repository.guardar(venta);
    
        // Act (Actuar)
        service.confirmar("V-123");

        // Assert (Afirmar / Comprobar)
        Venta ventaActualizada = repository.buscarPorId("V-123");
        assertNotNull(ventaActualizada);
        assertEquals(EstadoVenta.PAGADA, ventaActualizada.getEstado());
    }
}