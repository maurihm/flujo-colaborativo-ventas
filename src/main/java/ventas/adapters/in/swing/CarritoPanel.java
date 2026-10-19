package ventas.adapters.in.swing;

import ventas.application.DescuentoEstudiante;
import ventas.application.PoliticaDescuento;
import ventas.application.SinDescuento;
import ventas.domain.Dinero;
import ventas.domain.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<ItemCarrito> items = new ArrayList<>();
    private final DefaultTableModel tableModel;
    private final JTable tabla;
    private final JLabel lblSubtotal;
    private final JLabel lblIva;
    private final JLabel lblTotal;
    private final JComboBox<String> cmbDescuento;
    private final JButton btnConfirmarVenta;
    private final JButton btnLimpiarCarrito;

    public CarritoPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Carrito de Compras"));

        String[] cols = {"Código", "Producto", "Cant.", "Precio Unit.", "Subtotal"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(tableModel);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(2, 1, 4, 4));
        JPanel panelTotales = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        panelTotales.add(new JLabel("Descuento:"));
        cmbDescuento = new JComboBox<>(new String[]{"Sin Descuento", "Estudiante (10%)"});
        panelTotales.add(cmbDescuento);

        lblSubtotal = new JLabel("Subtotal: $0.00");
        lblIva = new JLabel("IVA (16%): $0.00");
        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 14f));

        panelTotales.add(lblSubtotal);
        panelTotales.add(lblIva);
        panelTotales.add(lblTotal);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLimpiarCarrito = new JButton("Vaciar Carrito");
        btnConfirmarVenta = new JButton("Confirmar Venta");
        btnConfirmarVenta.setBackground(new Color(40, 140, 40));
        btnConfirmarVenta.setForeground(Color.WHITE);

        panelBotones.add(btnLimpiarCarrito);
        panelBotones.add(btnConfirmarVenta);

        panelInferior.add(panelTotales);
        panelInferior.add(panelBotones);
        add(panelInferior, BorderLayout.SOUTH);

        cmbDescuento.addActionListener(e -> recalcular());
        btnLimpiarCarrito.addActionListener(e -> vaciar());
    }

    public void agregarItem(Producto prod, int cant) {
        if (prod == null || cant <= 0) {
            return;
        }
        items.add(new ItemCarrito(prod, cant));
        double sub = prod.getPrecio().getMonto() * cant;
        tableModel.addRow(new Object[]{
            prod.getCodigo(),
            prod.getNombre(),
            cant,
            String.format("$%.2f", prod.getPrecio().getMonto()),
            String.format("$%.2f", sub)
        });
        recalcular();
    }

    public void recalcular() {
        double subtotal = 0.0;
        for (ItemCarrito it : items) {
            subtotal += it.getSubtotal();
        }
        PoliticaDescuento politica = cmbDescuento.getSelectedIndex() == 1
                ? new DescuentoEstudiante()
                : new SinDescuento();
        Dinero conDescuento = politica.aplicar(new Dinero(subtotal));
        double iva = conDescuento.getMonto() * 0.16;
        double total = conDescuento.getMonto() + iva;

        lblSubtotal.setText(String.format("Subtotal: $%.2f", conDescuento.getMonto()));
        lblIva.setText(String.format("IVA (16%): $%.2f", iva));
        lblTotal.setText(String.format("TOTAL: $%.2f", total));
    }

    public void vaciar() {
        items.clear();
        tableModel.setRowCount(0);
        recalcular();
    }

    public List<ItemCarrito> getItems() {
        return new ArrayList<>(items);
    }

    public JButton getBtnConfirmarVenta() {
        return btnConfirmarVenta;
    }
}
