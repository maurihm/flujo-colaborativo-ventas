package ventas.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Invariante: la lista de detalles nunca se expone directamente,
// solo se modifica a través de agregarDetalle().
public class Venta {
    private Cliente cliente;
    private final List<DetalleVenta> detalles = new ArrayList<>();
    private EstadoVenta estado;
    private static final double TASA_IVA = 0.16; // 16%

    public Venta() {
        this.estado = EstadoVenta.NUEVA;
    }

    public void asignarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public void agregarDetalle(Producto producto, int cantidad) {
        if (this.estado != EstadoVenta.NUEVA) {
            throw new IllegalStateException("No se pueden agregar detalles a una venta " + this.estado);
        }
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