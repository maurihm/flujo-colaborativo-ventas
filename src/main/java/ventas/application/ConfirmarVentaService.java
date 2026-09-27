package ventas.application;

import ventas.domain.EstadoVenta;
import ventas.domain.Venta;
import ventas.ports.in.ConfirmarVentaUseCase;
import ventas.ports.out.VentaRepository;
import ventas.domain.VentaObserver;
import ventas.domain.VentaConfirmada;
import java.util.List;
import java.util.ArrayList;

public class ConfirmarVentaService implements ConfirmarVentaUseCase {
    private final VentaRepository repository;
    private final List<VentaObserver> observadores = new ArrayList<>();
    public ConfirmarVentaService(VentaRepository repository) {
        this.repository = repository;
    }

    public void agregarObservador(VentaObserver obs) {
        observadores.add(obs);
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
        
        observadores.forEach(o -> o.alConfirmar(new VentaConfirmada(venta)));
    }
}