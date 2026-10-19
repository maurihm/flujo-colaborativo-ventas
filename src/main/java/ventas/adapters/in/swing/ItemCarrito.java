package ventas.adapters.in.swing;

import ventas.domain.Producto;

public class ItemCarrito {

    private final Producto producto;
    private final int cantidad;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio().getMonto() * cantidad;
    }
}
