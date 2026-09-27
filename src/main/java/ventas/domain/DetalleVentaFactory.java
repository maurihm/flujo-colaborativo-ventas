package ventas.domain;

public class DetalleVentaFactory {

    private DetalleVentaFactory() {
    }
    
    public static DetalleVenta crear(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto es obligatorio para el detalle de venta");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        
        return new DetalleVenta(producto, cantidad);
    }
}
