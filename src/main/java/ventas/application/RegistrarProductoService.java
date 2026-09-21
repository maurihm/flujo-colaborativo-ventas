package ventas.application;
import ventas.domain.Producto;
import ventas.ports.in.RegistrarProductoUseCase;
import ventas.ports.out.ProductoRepository;

public class RegistrarProductoService implements RegistrarProductoUseCase {
    private final ProductoRepository repositorio;

    public RegistrarProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Producto registrar(ComandoRegistrarProducto comando) {
        Producto producto = new Producto(
                comando.getCodigo(), comando.getNombre(), comando.getPrecio(),
                comando.getExistencia(), comando.getCategoria());
        repositorio.guardar(producto);
        return producto;
    }
}