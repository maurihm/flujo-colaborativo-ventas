package ventas.adapters.in.swing;

import ventas.domain.Producto;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainFrame(VentaSwingController controller, ProductoTableModel tableModel) {
        super("Sistema de Ventas Cliente Swing (P07)");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        CatalogoPanel catalogoPanel = new CatalogoPanel(tableModel);
        FormularioProductoPanel formularioPanel = new FormularioProductoPanel();
        CarritoPanel carritoPanel = new CarritoPanel();

        JPanel panelAccionCatalogo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JSpinner spinCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JButton btnAgregarCarrito = new JButton("+ Agregar al Carrito");
        panelAccionCatalogo.add(new JLabel("Cantidad:"));
        panelAccionCatalogo.add(spinCantidad);
        panelAccionCatalogo.add(btnAgregarCarrito);
        catalogoPanel.add(panelAccionCatalogo, BorderLayout.SOUTH);

        btnAgregarCarrito.addActionListener(e -> {
            Producto seleccionado = catalogoPanel.getProductoSeleccionado();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto del catálogo.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int cant = (Integer) spinCantidad.getValue();
            carritoPanel.agregarItem(seleccionado, cant);
        });

        formularioPanel.getBtnRegistrar().addActionListener(e -> controller.registrarProductoAsync(formularioPanel, tableModel, this));

        carritoPanel.getBtnConfirmarVenta().addActionListener(e -> controller.procesarVentaAsync(carritoPanel, this));

        JSplitPane splitCentro = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, catalogoPanel, carritoPanel);
        splitCentro.setResizeWeight(0.55);
        add(formularioPanel, BorderLayout.NORTH);
        add(splitCentro, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1000, 700));
    }
}