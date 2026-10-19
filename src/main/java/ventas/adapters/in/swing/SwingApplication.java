package ventas.adapters.in.swing;

import ventas.adapters.out.memory.InMemoryProductoRepository;
import ventas.adapters.out.memory.InMemoryVentaRepository;
import ventas.application.ConfirmarVentaService;
import ventas.application.RegistrarProductoService;
import ventas.application.VentaFacade;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.out.ProductoRepository;
import ventas.ports.out.VentaRepository;
import javax.swing.*;

public class SwingApplication {
    public static void main(String[] args) {
        ProductoRepository productoRepo = new InMemoryProductoRepository();
        VentaRepository ventaRepo = new InMemoryVentaRepository();
        RegistrarProductoService registrarService = new RegistrarProductoService(productoRepo);
        ConfirmarVentaService confirmarService = new ConfirmarVentaService(ventaRepo);
        VentaFacade ventaFacade = new VentaFacade(ventaRepo, confirmarService);

        ProductoTableModel tableModel = new ProductoTableModel();
        Producto p1 = new Producto(1L, "Laptop Gamer", new Dinero(18500.00), 5, Categoria.ELECTRONICA);
        Producto p2 = new Producto(2L, "Mouse Inalámbrico", new Dinero(350.00), 20, Categoria.ELECTRONICA);
        productoRepo.guardar(p1);
        productoRepo.guardar(p2);
        tableModel.agregarProducto(p1);
        tableModel.agregarProducto(p2);

        VentaSwingController controller = new VentaSwingController(registrarService, ventaFacade);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { /* ignorado intencionalmente */ }
            MainFrame frame = new MainFrame(controller, tableModel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}