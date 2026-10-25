package ventas.adapters.out.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/ventas_tcsw";
    private static final String DEFAULT_USER = "ventas_user";
    private static final String DEFAULT_PASS = "ventas_pass";

    private ConexionBD() {
    }

    public static Connection obtenerConexion() throws SQLException {
        String url = System.getenv().getOrDefault("DB_URL", DEFAULT_URL);
        String user = System.getenv().getOrDefault("DB_USER", DEFAULT_USER);
        String pass = System.getenv().getOrDefault("DB_PASSWORD", DEFAULT_PASS);
        return DriverManager.getConnection(url, user, pass);
    }
}
