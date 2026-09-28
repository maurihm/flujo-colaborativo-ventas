package ventas.application;

import ventas.domain.*;
import ventas.ports.out.VentaRepository;

import java.util.List;

public class VentaFacade {
    private final VentaRepository repo;
    private final ConfirmarVentaService confirmarService;

    public VentaFacade(VentaRepository repo, ConfirmarVentaService confirmarService) {
        this.repo = repo;
        this.confirmarService = confirmarService;
    }

    public Venta procesarVentaCompleta(Cliente cliente, PoliticaDescuento politica, List<Producto> prods, List<Integer> cants) {
        // 1. Usar Factory
        Venta v = VentaFactory.crear(cliente);
        for(int i=0; i<prods.size(); i++) {
            v.agregarDetalle(DetalleVentaFactory.crear(prods.get(i), cants.get(i)));
        }
        
        // 2. Guardar (se le aplicaría el Strategy antes si se requiere)
        repo.guardar(v);
        
        // 3. Confirmar (Activa Observer)
        confirmarService.confirmar(v.getId());
        
        return v;
    }
}