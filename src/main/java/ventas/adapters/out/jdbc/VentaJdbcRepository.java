package ventas.adapters.out.jdbc;

import ventas.domain.Cliente;
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
        String sql = "INSERT INTO venta (id, cliente_codigo, cliente_nombre, cliente_apellido, cliente_telefono, total, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                     "ON CONFLICT (id) DO UPDATE SET " +
                     "total = EXCLUDED.total, estado = EXCLUDED.estado";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, venta.getId());
            if (venta.getCliente() != null) {
                ps.setLong(2, venta.getCliente().getCodigo());
                ps.setString(3, venta.getCliente().getNombre());
                ps.setString(4, venta.getCliente().getApellido());
                ps.setString(5, venta.getCliente().getTelefono());
            } else {
                ps.setLong(2, 0L);
                ps.setString(3, "General");
                ps.setString(4, "Publico");
                ps.setString(5, "N/A");
            }
            ps.setDouble(6, venta.calcularTotal().getMonto());
            ps.setString(7, venta.getEstado() != null ? venta.getEstado().name() : EstadoVenta.NUEVA.name());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar venta en BD: " + e.getMessage(), e);
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