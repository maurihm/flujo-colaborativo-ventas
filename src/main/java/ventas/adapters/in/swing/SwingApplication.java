package ventas.adapters.in.swing;

import ventas.adapters.out.jdbc.ConexionBD;
import ventas.adapters.out.jdbc.ProductoJdbcRepository;
import ventas.adapters.out.jdbc.VentaJdbcRepository;
import ventas.application.ConfirmarVentaService;
import ventas.application.RegistrarProductoService;
import ventas.application.VentaFacade;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.out.ProductoRepository;
import ventas.ports.out.VentaRepository;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SwingApplication {

    public static void main(String[] args) {
        // Adaptadores reales de persistencia relacional en PostgreSQL
        ProductoRepository productoRepo = new ProductoJdbcRepository();
        VentaRepository ventaRepo = new VentaJdbcRepository();

        RegistrarProductoService registrarService = new RegistrarProductoService(productoRepo);
        ConfirmarVentaService confirmarService = new ConfirmarVentaService(ventaRepo);
        VentaFacade ventaFacade = new VentaFacade(ventaRepo, confirmarService);

        ProductoTableModel tableModel = new ProductoTableModel();

        // Cargar productos existentes desde PostgreSQL al modelo de la tabla
        List<Producto> productosBD = cargarProductosDesdeBD();
        if (productosBD.isEmpty()) {
            // Semilla inicial si la base de datos está vacía
            Producto p1 = new Producto(1L, "Laptop Gamer Pro", new Dinero(18500.00), 5, Categoria.ELECTRONICA);
            Producto p2 = new Producto(2L, "Mouse Inalambrico", new Dinero(350.00), 20, Categoria.ELECTRONICA);
            Producto p3 = new Producto(3L, "Teclado Mecanico RGB", new Dinero(1200.00), 12, Categoria.ELECTRONICA);
            productoRepo.guardar(p1);
            productoRepo.guardar(p2);
            productoRepo.guardar(p3);
            tableModel.agregarProducto(p1);
            tableModel.agregarProducto(p2);
            tableModel.agregarProducto(p3);
        } else {
            for (Producto p : productosBD) {
                tableModel.agregarProducto(p);
            }
        }

        VentaSwingController controller = new VentaSwingController(registrarService, ventaFacade);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Look and feel por defecto
            }
            MainFrame frame = new MainFrame(controller, tableModel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static List<Producto> cargarProductosDesdeBD() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT codigo, nombre, precio, existencia, categoria FROM producto ORDER BY codigo ASC";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long cod = rs.getLong("codigo");
                String nom = rs.getString("nombre");
                double pre = rs.getDouble("precio");
                int exi = rs.getInt("existencia");
                Categoria cat = Categoria.valueOf(rs.getString("categoria"));
                lista.add(new Producto(cod, nom, new Dinero(pre), exi, cat));
            }
        } catch (SQLException e) {
            System.err.println("Aviso: No se pudo precargar catálogo desde PostgreSQL: " + e.getMessage());
        }
        return lista;
    }
}
