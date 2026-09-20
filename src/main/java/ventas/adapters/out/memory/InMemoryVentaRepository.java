package ventas.adapters.out.memory;

import ventas.domain.Venta;
import ventas.ports.out.VentaRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryVentaRepository implements VentaRepository {

    private final Map<String, Venta> baseDatosFicticia = new HashMap<>();

    @Override
    public void guardar(Venta venta) {
        baseDatosFicticia.put(venta.getId(), venta);
    }
}