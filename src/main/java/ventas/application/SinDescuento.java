package ventas.application;

import ventas.domain.Dinero;

public class SinDescuento implements PoliticaDescuento {
    @Override
    public Dinero aplicar(Dinero subtotal) {
        return subtotal;
    }

    @Override
    public String nombre() {
        return "Sin descuento";
    }
}