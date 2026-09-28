package ventas.application;

import ventas.domain.Venta;
import ventas.domain.VentaFactory;
import ventas.ports.in.CrearVentaUseCase;
import ventas.ports.out.VentaRepository;

public class CrearVentaService implements CrearVentaUseCase {

    private final VentaRepository ventaRepository;

    public CrearVentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public Venta crearVenta(ComandoCrearVenta comando) {
        
        Venta venta = VentaFactory.crear(comando.getCliente());
        
        ventaRepository.guardar(venta);
        
        return venta;
    }
}