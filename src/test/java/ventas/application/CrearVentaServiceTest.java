package ventas.application;

import org.junit.jupiter.api.Test;
import ventas.adapters.out.memory.InMemoryVentaRepository;
import ventas.domain.Cliente;
import ventas.domain.EstadoVenta;
import ventas.domain.Venta;
import ventas.ports.out.VentaRepository;

import static org.junit.jupiter.api.Assertions.*;

class CrearVentaServiceTest {

    @Test
    void debeCrearVentaExitosamente() {
        // Arrange (Preparar)
        VentaRepository repository = new InMemoryVentaRepository();
        CrearVentaService service = new CrearVentaService(repository);
        
        Cliente cliente = new Cliente(1L, "Juan", "Pérez", "123456789");
        ComandoCrearVenta comando = new ComandoCrearVenta(cliente);

        // Act (Actuar)
        Venta ventaCreada = service.crearVenta(comando);

        // Assert (Afirmar / Comprobar)
        assertNotNull(ventaCreada);
        assertEquals(cliente, ventaCreada.getCliente());
        assertEquals(EstadoVenta.NUEVA, ventaCreada.getEstado());
    }
}