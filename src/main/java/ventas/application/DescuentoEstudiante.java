package ventas.application;

import ventas.domain.Dinero;

public class DescuentoEstudiante implements PoliticaDescuento {
    @Override
    public Dinero aplicar(Dinero subtotal) {
        return subtotal.multiplicar(0.90); // Descuento del 10%
    }

    @Override
    public String nombre() {
        return "Descuento Estudiante 10%";
    }
}