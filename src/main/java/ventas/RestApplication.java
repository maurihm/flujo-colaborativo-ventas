package ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ventas.adapters.out.memory.InMemoryProductoRepository;
import ventas.adapters.out.memory.InMemoryVentaRepository;
import ventas.application.ConfirmarVentaService;
import ventas.application.RegistrarProductoService;
import ventas.application.VentaFacade;
import ventas.ports.in.RegistrarProductoUseCase;
import ventas.ports.out.ProductoRepository;
import ventas.ports.out.VentaRepository;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public ProductoRepository productoRepository() {
        return new InMemoryProductoRepository();
    }

    @Bean
    public VentaRepository ventaRepository() {
        return new InMemoryVentaRepository();
    }

    @Bean
    public RegistrarProductoUseCase registrarProductoUseCase(ProductoRepository repo) {
        return new RegistrarProductoService(repo);
    }

    @Bean
    public ConfirmarVentaService confirmarVentaService(VentaRepository repo) {
        return new ConfirmarVentaService(repo);
    }

    @Bean
    public VentaFacade ventaFacade(VentaRepository repo, ConfirmarVentaService confirmarService) {
        return new VentaFacade(repo, confirmarService);
    }
}
