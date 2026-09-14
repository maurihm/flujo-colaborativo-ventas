package ventas.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Invariante: la lista de detalles nunca se expone directamente,
// solo se modifica a través de agregarDetalle().
public class Venta {

    private final List<DetalleVenta> detalles = new ArrayList<>();
    private static final double TASA_IVA = 0.16; // 16%

    public void agregarDetalle(Producto producto, int cantidad) {
        detalles.add(new DetalleVenta(producto, cantidad));
    }

    public List<DetalleVenta> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }       

    // Calcula la suma limpia de los productos
    public Dinero calcularSubtotal() {
        Dinero subtotal = Dinero.CERO;
        for (DetalleVenta detalle : detalles) {
            subtotal = subtotal.sumar(detalle.subtotal());
        }
        return subtotal;
    }

    // Calcula el total aplicando el 16% de IVA al subtotal
    public Dinero calcularTotal() {
        Dinero subtotal = calcularSubtotal();
        Dinero montoIva = subtotal.multiplicar(TASA_IVA);
        return subtotal.sumar(montoIva);
    }
}
