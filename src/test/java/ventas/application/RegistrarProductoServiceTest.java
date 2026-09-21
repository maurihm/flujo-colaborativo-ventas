package ventas.application;
import org.junit.jupiter.api.Test;
import ventas.adapters.out.memory.InMemoryProductoRepository;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import static org.junit.jupiter.api.Assertions.*;

class RegistrarProductoServiceTest {
    @Test
    void registraUnProductoValidoYLoGuardaEnElRepositorio() {
        InMemoryProductoRepository repositorio = new InMemoryProductoRepository();
        RegistrarProductoService service = new RegistrarProductoService(repositorio);
        ComandoRegistrarProducto comando = new ComandoRegistrarProducto(1L, "Refresco", new Dinero(15.00), 10, Categoria.GENERAL);

        Producto producto = service.registrar(comando);

        assertEquals("Refresco", producto.getNombre());
        assertTrue(repositorio.buscarPorCodigo(1L).isPresent());
    }

    @Test
    void rechazaRegistrarUnProductoConNombreNulo() {
        InMemoryProductoRepository repositorio = new InMemoryProductoRepository();
        RegistrarProductoService service = new RegistrarProductoService(repositorio);
        Dinero precio = new Dinero(15.00);
        ComandoRegistrarProducto comando = new ComandoRegistrarProducto(1L, null, precio, 10, Categoria.GENERAL);

        assertThrows(IllegalArgumentException.class, () -> service.registrar(comando));
    }
}
