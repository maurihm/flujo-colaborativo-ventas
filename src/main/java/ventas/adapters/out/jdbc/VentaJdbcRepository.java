package ventas.adapters.out.jdbc;

import ventas.domain.Cliente;
import ventas.domain.DetalleVenta;
import ventas.domain.EstadoVenta;
import ventas.domain.Venta;
import ventas.ports.out.VentaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaJdbcRepository implements VentaRepository {

    private static final String SQL_INSERT_VENTA =
        "INSERT INTO venta (id, cliente_codigo, cliente_nombre, cliente_apellido, cliente_telefono, total, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?) " +
        "ON CONFLICT (id) DO UPDATE SET total = EXCLUDED.total, estado = EXCLUDED.estado";

    private static final String SQL_INSERT_DETALLE =
        "INSERT INTO detalle_venta (venta_id, producto_codigo, cantidad, precio_unitario, subtotal) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_DESCONTAR_STOCK =
        "UPDATE producto SET existencia = existencia - ? WHERE codigo = ? AND existencia >= ?";

    private static final String SQL_SELECT_VENTA =
        "SELECT id, cliente_codigo, cliente_nombre, cliente_apellido, cliente_telefono, total, estado FROM venta WHERE id = ?";

    @Override
    public void guardar(Venta venta) {
        Connection cn = null;
        try {
            cn = ConexionBD.obtenerConexion();
            cn.setAutoCommit(false);

            insertarEncabezado(cn, venta);
            insertarDetallesYDescontarStock(cn, venta);

            cn.commit();
        } catch (Exception e) {
            revertirSilenciosamente(cn);
            throw new RuntimeException("Transacción de venta fallida: " + e.getMessage(), e);
        } finally {
            cerrarSilenciosamente(cn);
        }
    }

    private void insertarEncabezado(Connection cn, Venta venta) throws SQLException {
        try (PreparedStatement psVenta = cn.prepareStatement(SQL_INSERT_VENTA)) {
            psVenta.setString(1, venta.getId());
            Cliente cliente = venta.getCliente();
            if (cliente != null) {
                psVenta.setLong(2, cliente.getCodigo());
                psVenta.setString(3, cliente.getNombre());
                psVenta.setString(4, cliente.getApellido());
                psVenta.setString(5, cliente.getTelefono());
            } else {
                psVenta.setLong(2, 0L);
                psVenta.setString(3, "General");
                psVenta.setString(4, "Publico");
                psVenta.setString(5, "N/A");
            }
            psVenta.setDouble(6, venta.calcularTotal().getMonto());
            psVenta.setString(7, venta.getEstado() != null ? venta.getEstado().name() : EstadoVenta.NUEVA.name());
            psVenta.executeUpdate();
        }
    }

    private void insertarDetallesYDescontarStock(Connection cn, Venta venta) throws SQLException {
        for (DetalleVenta d : venta.getDetalles()) {
            insertarDetalle(cn, venta.getId(), d);
            descontarStock(cn, d);
        }
    }

    private void insertarDetalle(Connection cn, String ventaId, DetalleVenta d) throws SQLException {
        try (PreparedStatement psDetalle = cn.prepareStatement(SQL_INSERT_DETALLE)) {
            psDetalle.setString(1, ventaId);
            psDetalle.setLong(2, d.getProducto().getCodigo());
            psDetalle.setInt(3, d.getCantidad());
            psDetalle.setDouble(4, d.getPrecioCapturado().getMonto());
            psDetalle.setDouble(5, d.subtotal().getMonto());
            psDetalle.executeUpdate();
        }
    }

    private void descontarStock(Connection cn, DetalleVenta d) throws SQLException {
        try (PreparedStatement psStock = cn.prepareStatement(SQL_DESCONTAR_STOCK)) {
            psStock.setInt(1, d.getCantidad());
            psStock.setLong(2, d.getProducto().getCodigo());
            psStock.setInt(3, d.getCantidad());
            int filasActualizadas = psStock.executeUpdate();
            if (filasActualizadas == 0) {
                throw new IllegalStateException("Stock insuficiente para el producto codigo: " + d.getProducto().getCodigo());
            }
        }
    }

    private void revertirSilenciosamente(Connection cn) {
        if (cn != null) {
            try {
                cn.rollback();
            } catch (SQLException exRollback) {
                // Se preserva la excepcion original de la transaccion
            }
        }
    }

    private void cerrarSilenciosamente(Connection cn) {
        if (cn != null) {
            try {
                cn.setAutoCommit(true);
                cn.close();
            } catch (SQLException ignored) {
                // Se ignora error al cerrar la conexion en bloque finally
            }
        }
    }

    @Override
    public Venta buscarPorId(String id) {
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_VENTA)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Venta venta = new Venta(rs.getString("id"));
                    long cliCod = rs.getLong("cliente_codigo");
                    String cliNom = rs.getString("cliente_nombre");
                    String cliApe = rs.getString("cliente_apellido");
                    String cliTel = rs.getString("cliente_telefono");

                    venta.asignarCliente(new Cliente(cliCod, cliNom, cliApe, cliTel));
                    venta.setEstado(EstadoVenta.valueOf(rs.getString("estado")));
                    return venta;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar venta en BD: " + e.getMessage(), e);
        }
        return null;
    }
}
