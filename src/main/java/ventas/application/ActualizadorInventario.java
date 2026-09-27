package ventas.application;

import ventas.domain.VentaConfirmada;
import ventas.domain.VentaObserver;

public class ActualizadorInventario implements VentaObserver {
    @Override
    public void alConfirmar(VentaConfirmada evento) {
        evento.getVenta().getDetalles().forEach(d -> 
            d.getProducto().descontar(d.getCantidad())
        );
    }
}
