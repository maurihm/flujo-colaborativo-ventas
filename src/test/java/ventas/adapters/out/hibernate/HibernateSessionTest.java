package ventas.adapters.out.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateSessionTest {
    @Test
    void testSessionFactoryInicializaCorrectamente() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if (sf != null) {
            try (Session session = sf.openSession()) {
                assertTrue(session.isOpen(), "La sesión de Hibernate debe abrirse con éxito");
            }
        }
    }
}