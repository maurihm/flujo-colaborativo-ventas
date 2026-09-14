package ventas.domain;

public class Producto {

    private long codigo;
    private String nombre;
    private Dinero precio;
    private int existencia;
    private Categoria categoria;

    public Producto(long codigo, String nombre, Dinero precio, int existencia, Categoria categoria) {
        if (precio == null || existencia < 0 || nombre == null || nombre.isBlank() || codigo <= 0 || categoria == null) {
            throw new IllegalArgumentException("Datos de producto inválidos");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
        this.categoria = categoria;
    }

    public Producto(long codigo, String nombre, Dinero precio, int existencia) {
        this(codigo, nombre, precio, existencia, Categoria.GENERAL);
    }

    public void descontar(int cantidad) {
        if (cantidad <= 0 || cantidad > existencia) {
            throw new IllegalArgumentException("Cantidad inválida para descontar");
        }
        existencia -= cantidad;
    }

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

    public Categoria getCategoria() {
        return categoria;
    }
}