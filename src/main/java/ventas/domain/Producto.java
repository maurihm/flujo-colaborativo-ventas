package ventas.domain;

// Invariantes: el precio no puede ser negativo (lo protege Dinero) y la existencia no puede ser negativa.
public class Producto {

    private long codigo;
    private String nombre;
    private Dinero precio;
    private int existencia;

    public Producto(long codigo, String nombre, Dinero precio, int existencia) {
        if (precio == null || existencia < 0 || nombre == null || nombre.isBlank() || codigo <= 0) {
            throw new IllegalArgumentException("Datos de producto inválidos");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
    }

    // Protege el estado: solo descuenta si la cantidad es válida y hay existencia suficiente.
    public void descontar(int cantidad) {
        if (cantidad <= 0 || cantidad > existencia) {
            throw new IllegalArgumentException("Cantidad inválida para descontar");
        }
        existencia -= cantidad;
    }

    // Permite que el precio del catálogo cambie con el tiempo, sin afectar ventas ya registradas.
    public void actualizarPrecio(Dinero nuevoPrecio) {
        if (nuevoPrecio == null) {
            throw new IllegalArgumentException("El precio no puede ser nulo");
        }
        precio = nuevoPrecio;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Dinero getPrecio() {
        return precio;
    }

    public int getExistencia() {
        return existencia;
    }
}
