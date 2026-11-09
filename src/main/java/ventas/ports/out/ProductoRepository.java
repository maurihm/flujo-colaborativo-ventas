package ventas.ports.out;
import ventas.domain.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    void guardar(Producto producto);
    Optional<Producto> buscarPorCodigo(long codigo);
    List<Producto> listarTodos();
}
