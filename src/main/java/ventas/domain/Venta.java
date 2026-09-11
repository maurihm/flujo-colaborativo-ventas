package ventas.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Invariante: la lista de detalles nunca se expone directamente,
// solo se modifica a través de agregarDetalle().
public class Venta {

    private final List<DetalleVenta> detalles = new ArrayList<>();

    public void agregarDetalle(Producto producto, int cantidad) {
        detalles.add(new DetalleVenta(producto, cantidad));
    }

    public List<DetalleVenta> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    public Dinero calcularTotal() {
        Dinero total = Dinero.CERO;
        for (DetalleVenta detalle : detalles) {
            total = total.sumar(detalle.subtotal());
        }
        return total;
    }
}
