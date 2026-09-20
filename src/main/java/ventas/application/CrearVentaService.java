package ventas.application;
import ventas.domain.Venta;
import ventas.ports.in.CrearVentaUseCase;
import ventas.ports.out.VentaRepository;


public class CrearVentaService implements CrearVentaUseCase {

    private final VentaRepository ventaRepository;

    public CrearVentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public Venta crearVenta(ComandoCrearVenta comando) {
        // Crear la instancia usando el constructor vacío que por defecto la pone en estado NUEVA
        Venta venta = new Venta();
        
        // Asignar el cliente recibido en el comando utilizando el método del dominio
        venta.asignarCliente(comando.getCliente());
        
        // Guardar la venta utilizando el puerto de salida
        ventaRepository.guardar(venta);
        
        return venta;
    }
}