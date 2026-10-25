package ventas.adapters.out.jdbc;

import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.out.ProductoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductoJdbcRepository implements ProductoRepository {

    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT INTO producto (codigo, nombre, precio, existencia, categoria) " +
                     "VALUES (?, ?, ?, ?, ?) " +
                     "ON CONFLICT (codigo) DO UPDATE SET " +
                     "nombre = EXCLUDED.nombre, " +
                     "precio = EXCLUDED.precio, " +
                     "existencia = EXCLUDED.existencia, " +
                     "categoria = EXCLUDED.categoria";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getPrecio().getMonto());
            ps.setInt(4, producto.getExistencia());
            ps.setString(5, producto.getCategoria().name());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar producto en BD: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Producto> buscarPorCodigo(long codigo) {
        String sql = "SELECT codigo, nombre, precio, existencia, categoria FROM producto WHERE codigo = ?";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long cod = rs.getLong("codigo");
                    String nom = rs.getString("nombre");
                    double pre = rs.getDouble("precio");
                    int exi = rs.getInt("existencia");
                    Categoria cat = Categoria.valueOf(rs.getString("categoria"));

                    return Optional.of(new Producto(cod, nom, new Dinero(pre), exi, cat));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar producto en BD: " + e.getMessage(), e);
        }

        return Optional.empty();
    }
}
