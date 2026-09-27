package ventas.domain;

public class VentaFactory {

    private VentaFactory() {
    }
    
    public static Venta crear(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio para crear una venta");
        }
        
        Venta venta = new Venta();
        venta.asignarCliente(cliente);
        return venta;
    }
}
