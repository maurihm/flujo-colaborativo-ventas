package ventas.adapters.out.memory;
import ventas.domain.Producto;
import ventas.ports.out.ProductoRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductoRepository implements ProductoRepository {
    private final Map<Long, Producto> productos = new HashMap<>();

    @Override
    public void guardar(Producto producto) {
        productos.put(producto.getCodigo(), producto);
    }

    @Override
    public Optional<Producto> buscarPorCodigo(long codigo) {
        return Optional.ofNullable(productos.get(codigo));
    }
}
