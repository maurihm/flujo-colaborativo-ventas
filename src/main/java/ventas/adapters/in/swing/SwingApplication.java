package ventas.adapters.in.swing;

import ventas.adapters.out.hibernate.ProductoHibernateRepository;
import ventas.adapters.out.hibernate.VentaHibernateRepository;
import ventas.application.ConfirmarVentaService;
import ventas.application.RegistrarProductoService;
import ventas.application.VentaFacade;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.out.ProductoRepository;
import ventas.ports.out.VentaRepository;

import javax.swing.*;
import java.util.List;

public class SwingApplication {

    public static void main(String[] args) {
        ProductoRepository productoRepo = new ProductoHibernateRepository();
        VentaRepository ventaRepo = new VentaHibernateRepository();

        RegistrarProductoService registrarService = new RegistrarProductoService(productoRepo);
        ConfirmarVentaService confirmarService = new ConfirmarVentaService(ventaRepo);
        VentaFacade ventaFacade = new VentaFacade(ventaRepo, confirmarService);
        VentaSwingController controller = new VentaSwingController(registrarService, ventaFacade);

        List<Producto> productosBD = productoRepo.listarTodos();
        if (productosBD.isEmpty()) {
            Producto p1 = new Producto(1L, "Laptop Gamer Pro", new Dinero(18500.00), 5, Categoria.ELECTRONICA);
            Producto p2 = new Producto(2L, "Mouse Inalambrico", new Dinero(350.00), 20, Categoria.ELECTRONICA);
            Producto p3 = new Producto(3L, "Teclado Mecanico RGB", new Dinero(1200.00), 12, Categoria.ELECTRONICA);
            productoRepo.guardar(p1);
            productoRepo.guardar(p2);
            productoRepo.guardar(p3);
            productosBD = productoRepo.listarTodos();
        }

        final List<Producto> productosParaMostrar = productosBD;

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            ProductoTableModel tableModel = new ProductoTableModel();
            for (Producto p : productosParaMostrar) {
                tableModel.agregarProducto(p);
            }
            MainFrame frame = new MainFrame(controller, tableModel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
