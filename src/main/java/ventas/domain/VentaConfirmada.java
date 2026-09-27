package ventas.domain;

public class VentaConfirmada {
    private final Venta venta;
    
    public VentaConfirmada(Venta venta) { 
        this.venta = venta; 
    }
    
    public Venta getVenta() { 
        return venta; 
    }
}
