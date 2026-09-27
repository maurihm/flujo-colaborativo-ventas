package ventas.application;

import ventas.domain.Dinero;

public interface PoliticaDescuento {
    Dinero aplicar(Dinero subtotal);
    String nombre();
}