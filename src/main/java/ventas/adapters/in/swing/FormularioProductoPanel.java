package ventas.adapters.in.swing;

import ventas.domain.Categoria;
import javax.swing.*;
import java.awt.*;

public class FormularioProductoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JTextField txtCodigo;
    private final JTextField txtNombre;
    private final JTextField txtPrecio;
    private final JTextField txtStock;
    private final JComboBox<Categoria> cmbCategoria;
    private final JButton btnRegistrar;
    private final JButton btnLimpiar;

    public FormularioProductoPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Registro de Nuevo Producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtCodigo = new JTextField(10);
        txtNombre = new JTextField(15);
        txtPrecio = new JTextField(10);
        txtStock = new JTextField(8);
        cmbCategoria = new JComboBox<>(Categoria.values());
        btnRegistrar = new JButton("Registrar Producto");
        btnLimpiar = new JButton("Limpiar Campos");

        agregarFila(0, "Código:", txtCodigo, gbc);
        agregarFila(1, "Nombre:", txtNombre, gbc);
        agregarFila(2, "Precio ($):", txtPrecio, gbc);
        agregarFila(3, "Existencia:", txtStock, gbc);
        agregarFila(4, "Categoria:", cmbCategoria, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegistrar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);

        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void agregarFila(int fila, String etiqueta, JComponent campo, GridBagConstraints gbc) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setLabelFor(campo);
        add(lbl, gbc);

        gbc.gridx = 1;
        add(campo, gbc);
    }

    public void validarEntradas() {
        if (txtCodigo.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtPrecio.getText().trim().isEmpty() ||
            txtStock.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos del formulario son obligatorios.");
        }

        try {
            long cod = Long.parseLong(txtCodigo.getText().trim());
            if (cod <= 0) throw new IllegalArgumentException("El código debe ser un entero positivo.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El código debe ser numérico.");
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio debe ser un número decimal válido.");
        }

        try {
            int stock = Integer.parseInt(txtStock.getText().trim());
            if (stock < 0) throw new IllegalArgumentException("La existencia no puede ser negativa.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La existencia debe ser un entero válido.");
        }
    }

    public void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        cmbCategoria.setSelectedIndex(0);
        txtCodigo.requestFocus();
    }

    public long getCodigo() { return Long.parseLong(txtCodigo.getText().trim()); }
    public String getNombre() { return txtNombre.getText().trim(); }
    public double getPrecio() { return Double.parseDouble(txtPrecio.getText().trim()); }
    public int getStock() { return Integer.parseInt(txtStock.getText().trim()); }
    public Categoria getCategoria() { return (Categoria) cmbCategoria.getSelectedItem(); }
    public JButton getBtnRegistrar() { return btnRegistrar; }
}