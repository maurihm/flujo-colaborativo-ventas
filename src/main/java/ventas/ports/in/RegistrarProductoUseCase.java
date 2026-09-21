package ventas.ports.in;
import ventas.application.ComandoRegistrarProducto;
import ventas.domain.Producto;

public interface RegistrarProductoUseCase {
    Producto registrar(ComandoRegistrarProducto comando);
}