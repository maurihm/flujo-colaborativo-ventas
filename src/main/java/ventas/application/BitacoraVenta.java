package ventas.application;

import ventas.domain.VentaConfirmada;
import ventas.domain.VentaObserver;

public class BitacoraVenta implements VentaObserver {
    @Override
    public void alConfirmar(VentaConfirmada evento) {
        System.out.println("Bitácora: Venta confirmada con ID " + evento.getVenta().getId());
    }
}
