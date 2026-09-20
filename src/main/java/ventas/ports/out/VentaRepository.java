package ventas.ports.out;

import ventas.domain.Venta;

public interface VentaRepository {
    void guardar(Venta venta);
}