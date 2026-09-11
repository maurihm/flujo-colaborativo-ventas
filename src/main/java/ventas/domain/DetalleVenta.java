package ventas.domain;

// Invariante: la cantidad debe ser mayor que cero.
// El precio se captura al crear el detalle y ya no cambia, aunque después
// cambie el precio del producto en el catálogo.
public class DetalleVenta {

    private final Producto producto;
    private final int cantidad;
    private final Dinero precioCapturado;

    public DetalleVenta(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioCapturado = producto.getPrecio();
    }

    public Dinero subtotal() {
        return precioCapturado.multiplicar(cantidad);
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Dinero getPrecioCapturado() {
        return precioCapturado;
    }
}
