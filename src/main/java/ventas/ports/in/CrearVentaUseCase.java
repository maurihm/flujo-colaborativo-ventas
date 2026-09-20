package ventas.ports.in;

import ventas.application.ComandoCrearVenta;
import ventas.domain.Venta;

public interface CrearVentaUseCase {
    Venta crearVenta(ComandoCrearVenta comando);
}