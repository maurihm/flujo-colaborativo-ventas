package ventas.adapters.in.swing;

import ventas.domain.Producto;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductoTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final String[] columnas = {"Código", "Nombre", "Precio", "Stock", "Categoría"};
    private final List<Producto> productos;

    public ProductoTableModel() {
        this.productos = new ArrayList<>();
    }

    public ProductoTableModel(List<Producto> iniciales) {
        this.productos = new ArrayList<>(iniciales);
    }

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= productos.size()) {
            return null;
        }
        Producto p = productos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getCodigo();
            case 1:
                return p.getNombre();
            case 2:
                return String.format("$%.2f", p.getPrecio().getMonto());
            case 3:
                return p.getExistencia();
            case 4:
                return p.getCategoria();
            default:
                return null;
        }
    }

    public void agregarProducto(Producto p) {
        if (p == null) {
            return;
        }
        productos.add(p);
        int fila = productos.size() - 1;
        fireTableRowsInserted(fila, fila);
    }

    public void setProductos(List<Producto> nuevos) {
        productos.clear();
        if (nuevos != null) {
            productos.addAll(nuevos);
        }
        fireTableDataChanged();
    }

    public Producto getProductoAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < productos.size()) {
            return productos.get(rowIndex);
        }
        return null;
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }
}
