package ventas.adapters.out.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ventas.adapters.out.hibernate.entity.DetalleVentaEntity;
import ventas.adapters.out.hibernate.entity.ProductoEntity;
import ventas.adapters.out.hibernate.entity.VentaEntity;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/ventas_tcsw");
                String user = System.getenv().getOrDefault("DB_USER", "ventas_user");
                String pass = System.getenv().getOrDefault("DB_PASSWORD", "ventas_pass");

                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", url);
                configuration.setProperty("hibernate.connection.username", user);
                configuration.setProperty("hibernate.connection.password", pass);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
                configuration.setProperty("hibernate.show_sql", "false");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");

                configuration.addAnnotatedClass(ProductoEntity.class);
                configuration.addAnnotatedClass(VentaEntity.class);
                configuration.addAnnotatedClass(DetalleVentaEntity.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Error al inicializar Hibernate SessionFactory: " + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}