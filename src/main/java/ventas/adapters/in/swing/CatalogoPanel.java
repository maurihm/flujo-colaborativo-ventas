package ventas.adapters.in.swing;

import ventas.domain.Producto;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;

public class CatalogoPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JTable tabla;
    private final ProductoTableModel tableModel;

    public CatalogoPanel(ProductoTableModel tableModel) {
        this.tableModel = tableModel;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder("Catálogo de Productos Disponibles"));

        tabla = new JTable(tableModel);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getAccessibleContext().setAccessibleName("Tabla de Catálogo de Productos");

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    public Producto getProductoSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row != -1) {
            return tableModel.getProductoAt(row);
        }
        return null;
    }

    public JTable getTabla() {
        return tabla;
    }

    public ProductoTableModel getTableModel() {
        return tableModel;
    }
}
