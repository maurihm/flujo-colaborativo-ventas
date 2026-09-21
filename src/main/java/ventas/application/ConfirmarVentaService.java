package ventas.application;

import ventas.domain.EstadoVenta;
import ventas.domain.Venta;
import ventas.ports.in.ConfirmarVentaUseCase;
import ventas.ports.out.VentaRepository;

public class ConfirmarVentaService implements ConfirmarVentaUseCase {
    private final VentaRepository repository;

    public ConfirmarVentaService(VentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void confirmar(String ventaId) {
        Venta venta = repository.buscarPorId(ventaId);
        
        if (venta == null) {
            throw new IllegalArgumentException("La venta no existe");
        }
        
        // Lógica de dominio: cambiamos el estado
        venta.setEstado(EstadoVenta.PAGADA);
        
        // Guardamos el cambio en el repositorio
        repository.guardar(venta);
    }
}