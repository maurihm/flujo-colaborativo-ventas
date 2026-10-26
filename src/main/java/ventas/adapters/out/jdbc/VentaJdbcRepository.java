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

    @Override
    public void guardar(Venta venta) {
        String sqlVenta = "INSERT INTO venta (id, cliente_codigo, cliente_nombre, cliente_apellido, cliente_telefono, total, estado) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                          "ON CONFLICT (id) DO UPDATE SET total = EXCLUDED.total, estado = EXCLUDED.estado";
        String sqlDetalle = "INSERT INTO detalle_venta (venta_id, producto_codigo, cantidad, precio_unitario, subtotal) " +
                            "VALUES (?, ?, ?, ?, ?)";
        String sqlDescontarStock = "UPDATE producto SET existencia = existencia - ? WHERE codigo = ? AND existencia >= ?";

        Connection cn = null;
        try {
            cn = ConexionBD.obtenerConexion();
            cn.setAutoCommit(false); // Inicia frontera transaccional M11

            // 1. Insertar encabezado de venta
            try (PreparedStatement psVenta = cn.prepareStatement(sqlVenta)) {
                psVenta.setString(1, venta.getId());
                if (venta.getCliente() != null) {
                    psVenta.setLong(2, venta.getCliente().getCodigo());
                    psVenta.setString(3, venta.getCliente().getNombre());
                    psVenta.setString(4, venta.getCliente().getApellido());
                    psVenta.setString(5, venta.getCliente().getTelefono());
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

            // 2. Insertar detalles y descontar existencia atómicamente
            if (!venta.getDetalles().isEmpty()) {
                try (PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);
                     PreparedStatement psStock = cn.prepareStatement(sqlDescontarStock)) {
                    for (DetalleVenta d : venta.getDetalles()) {
                        psDetalle.setString(1, venta.getId());
                        psDetalle.setLong(2, d.getProducto().getCodigo());
                        psDetalle.setInt(3, d.getCantidad());
                        psDetalle.setDouble(4, d.getPrecioCapturado().getMonto());
                        psDetalle.setDouble(5, d.subtotal().getMonto());
                        psDetalle.executeUpdate();

                        psStock.setInt(1, d.getCantidad());
                        psStock.setLong(2, d.getProducto().getCodigo());
                        psStock.setInt(3, d.getCantidad());
                        int filasActualizadas = psStock.executeUpdate();
                        if (filasActualizadas == 0) {
                            throw new IllegalStateException("Stock insuficiente para el producto codigo: " + d.getProducto().getCodigo());
                        }
                    }
                }
            }

            cn.commit(); // Confirmación de la unidad atómica
        } catch (Exception e) {
            if (cn != null) {
                try {
                    cn.rollback(); // Reversión completa ante fallo
                } catch (SQLException exRollback) {
                    // Se preserva la causa raíz
                }
            }
            throw new RuntimeException("Transacción de venta fallida: " + e.getMessage(), e);
        } finally {
            if (cn != null) {
                try {
                    cn.setAutoCommit(true);
                    cn.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    @Override
    public Venta buscarPorId(String id) {
        String sql = "SELECT id, cliente_codigo, cliente_nombre, cliente_apellido, cliente_telefono, total, estado FROM venta WHERE id = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
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
