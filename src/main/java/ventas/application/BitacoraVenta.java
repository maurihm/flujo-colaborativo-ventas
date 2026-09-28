package ventas.application;

import ventas.domain.VentaConfirmada;
import ventas.domain.VentaObserver;
import java.util.logging.Logger;

public class BitacoraVenta implements VentaObserver {
    private static final Logger logger = Logger.getLogger(BitacoraVenta.class.getName());

    @Override
    public void alConfirmar(VentaConfirmada evento) {
        logger.info("Bitácora: Venta confirmada con ID " + evento.getVenta().getId());
    }
}