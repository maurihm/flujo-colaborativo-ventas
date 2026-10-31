# Evidencia de Contribución C02 —
**Integrante:** Mauricio Hernández Martínez
**Módulo y Componente Asignado:** Corte 2 — Adaptadores de Persistencia Hibernate y Coordinación Transaccional

## Resumen de la Contribución
Desarrollé las implementaciones de los puertos de persistencia con Hibernate (`ProductoHibernateRepository` y
`VentaHibernateRepository`), coordinando la transacción atómica de negocio mediante `Session` y `Transaction`
para garantizar la indivisibilidad entre la inserción de la venta, las partidas de detalle y el descuento de existencias en el inventario.

### Problema Resuelto
La arquitectura hexagonal exige que los adaptadores de infraestructura puedan intercambiarse sin alterar las interfaces del dominio (`ProductoRepository`, `VentaRepository`). Asimismo, el criterio 3 de R03 requiere demostrar consistencia transaccional bajo la nueva capa de persistencia en PostgreSQL con Hibernate.

### Solución Implementada
- **`ProductoHibernateRepository.java`:** Implementación del puerto con `session.saveOrUpdate()` y mapeo seguro de `ProductoEntity`.
- **`VentaHibernateRepository.java`:** Coordinación transaccional con `session.beginTransaction()`, guardado del agregado `Venta`, inserción de `DetalleVentaEntity` y actualización atómica del stock en `ProductoEntity`, confirmando con `tx.commit()`. Un producto inexistente o con existencia insuficiente lanza excepción y provoca `tx.rollback()` de toda la venta.
- **`TransaccionHibernateVentaTest.java`:** Prueba de integración que comprueba la persistencia de la venta y la reducción exacta del stock en PostgreSQL.

### Evidencias en GitHub
- **Rama:** `feature/c02-hibernate-repositorios-transaccion`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/48
- **Pull Request (PR):** [PEGAR LINK DEL PR DE MAURICIO]
- **Revisor Asignado:** Axel Uriel García Loyo
