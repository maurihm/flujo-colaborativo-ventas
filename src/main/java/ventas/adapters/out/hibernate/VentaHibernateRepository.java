package ventas.adapters.out.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ventas.adapters.out.hibernate.entity.DetalleVentaEntity;
import ventas.adapters.out.hibernate.entity.ProductoEntity;
import ventas.adapters.out.hibernate.entity.VentaEntity;
import ventas.domain.Cliente;
import ventas.domain.DetalleVenta;
import ventas.domain.EstadoVenta;
import ventas.domain.Venta;
import ventas.ports.out.VentaRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Adaptador de salida: implementa el puerto {@link VentaRepository} con Hibernate.
 * El guardado de una venta es una unidad atómica: encabezado, detalles y descuento
 * de existencias se confirman juntos (commit) o se revierten juntos (rollback).
 */
public class VentaHibernateRepository implements VentaRepository {

    private final SessionFactory sessionFactory;

    public VentaHibernateRepository() {
        this(HibernateUtil.getSessionFactory());
    }

    public VentaHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Venta venta) {
        if (sessionFactory == null) {
            return;
        }
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            VentaEntity ventaEntity = mapearEncabezado(venta);
            session.save(ventaEntity);

            for (DetalleVenta d : venta.getDetalles()) {
                persistirDetalleYDescontarStock(session, ventaEntity, d);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error en transaccion Hibernate de Registrar Venta: " + e.getMessage(), e);
        }
    }

    private VentaEntity mapearEncabezado(Venta venta) {
        VentaEntity ventaEntity = new VentaEntity();
        ventaEntity.setId(venta.getId());
        Cliente cl = venta.getCliente();
        if (cl != null) {
            ventaEntity.setClienteCodigo(cl.getCodigo());
            ventaEntity.setClienteNombre(cl.getNombre());
            ventaEntity.setClienteApellido(cl.getApellido());
            ventaEntity.setClienteTelefono(cl.getTelefono() != null ? cl.getTelefono() : "");
        } else {
            ventaEntity.setClienteCodigo(0L);
            ventaEntity.setClienteNombre("General");
            ventaEntity.setClienteApellido("Publico");
            ventaEntity.setClienteTelefono("N/A");
        }
        ventaEntity.setTotal(BigDecimal.valueOf(venta.calcularTotal().getMonto()));
        ventaEntity.setEstado(venta.getEstado() != null ? venta.getEstado().name() : EstadoVenta.NUEVA.name());
        ventaEntity.setFecha(new Timestamp(System.currentTimeMillis()));
        return ventaEntity;
    }

    private void persistirDetalleYDescontarStock(Session session, VentaEntity ventaEntity, DetalleVenta d) {
        long codigo = d.getProducto().getCodigo();

        // Validar antes de insertar: un producto inexistente o sin stock aborta toda la transacción.
        ProductoEntity prodEntity = session.get(ProductoEntity.class, codigo);
        if (prodEntity == null) {
            throw new IllegalStateException("Producto inexistente en inventario: " + codigo);
        }
        if (prodEntity.getExistencia() < d.getCantidad()) {
            throw new IllegalStateException("Existencia insuficiente para producto: " + codigo);
        }

        DetalleVentaEntity detalleEntity = new DetalleVentaEntity();
        detalleEntity.setVenta(ventaEntity);
        detalleEntity.setProductoCodigo(codigo);
        detalleEntity.setCantidad(d.getCantidad());
        detalleEntity.setPrecioUnitario(BigDecimal.valueOf(d.getPrecioCapturado().getMonto()));
        detalleEntity.setSubtotal(BigDecimal.valueOf(d.subtotal().getMonto()));
        session.save(detalleEntity);

        prodEntity.setExistencia(prodEntity.getExistencia() - d.getCantidad());
        session.update(prodEntity);
    }

    @Override
    public Venta buscarPorId(String id) {
        if (sessionFactory == null) {
            return null;
        }
        try (Session session = sessionFactory.openSession()) {
            VentaEntity entity = session.get(VentaEntity.class, id);
            if (entity != null) {
                Venta venta = new Venta(entity.getId());
                venta.asignarCliente(new Cliente(
                        entity.getClienteCodigo(),
                        entity.getClienteNombre(),
                        entity.getClienteApellido(),
                        entity.getClienteTelefono()
                ));
                venta.setEstado(EstadoVenta.valueOf(entity.getEstado()));
                return venta;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar venta con Hibernate: " + e.getMessage(), e);
        }
        return null;
    }
}
