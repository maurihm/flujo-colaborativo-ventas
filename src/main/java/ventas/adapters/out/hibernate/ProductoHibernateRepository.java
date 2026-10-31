package ventas.adapters.out.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ventas.adapters.out.hibernate.entity.ProductoEntity;
import ventas.domain.Categoria;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.ports.out.ProductoRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Adaptador de salida: implementa el puerto {@link ProductoRepository}
 * usando Hibernate sobre PostgreSQL. El dominio permanece libre de anotaciones JPA.
 */
public class ProductoHibernateRepository implements ProductoRepository {

    private final SessionFactory sessionFactory;

    public ProductoHibernateRepository() {
        this(HibernateUtil.getSessionFactory());
    }

    public ProductoHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Producto producto) {
        if (sessionFactory == null) {
            return;
        }
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            ProductoEntity entity = new ProductoEntity(
                    producto.getCodigo(),
                    producto.getNombre(),
                    BigDecimal.valueOf(producto.getPrecio().getMonto()),
                    producto.getExistencia(),
                    producto.getCategoria().name()
            );
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new IllegalStateException("Error al guardar producto con Hibernate: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Producto> buscarPorCodigo(long codigo) {
        if (sessionFactory == null) {
            return Optional.empty();
        }
        try (Session session = sessionFactory.openSession()) {
            ProductoEntity entity = session.get(ProductoEntity.class, codigo);
            if (entity != null) {
                return Optional.of(new Producto(
                        entity.getCodigo(),
                        entity.getNombre(),
                        new Dinero(entity.getPrecio().doubleValue()),
                        entity.getExistencia(),
                        Categoria.valueOf(entity.getCategoria())
                ));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error al consultar producto con Hibernate: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}
