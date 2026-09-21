package ventas.application;
import ventas.domain.Categoria;
import ventas.domain.Dinero;

public class ComandoRegistrarProducto {
    private final long codigo;
    private final String nombre;
    private final Dinero precio;
    private final int existencia;
    private final Categoria categoria;

    public ComandoRegistrarProducto(long codigo, String nombre, Dinero precio, int existencia, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
        this.categoria = categoria;
    }

    public long getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Dinero getPrecio() { return precio; }
    public int getExistencia() { return existencia; }
    public Categoria getCategoria() { return categoria; }
}