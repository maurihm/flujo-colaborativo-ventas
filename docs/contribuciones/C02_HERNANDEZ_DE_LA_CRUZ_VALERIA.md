# Evidencia de Contribución C02
**Integrante:** Valeria Hernández De la Cruz  
**Módulo y Componente Asignado:** Corte 2 Configuración de Hibernate 5.6 y Mapeo Objeto-Relacional JPA  

## Resumen de la Contribución
Configuré la infraestructura del ORM Hibernate en pom.xml, implementé la factoría HibernateUtil con resolución dinámica de variables de entorno y creé el modelo de entidades relacionales (ProductoEntity, VentaEntity, DetalleVentaEntity) bajo el paquete de adaptadores, salvaguardando la pureza del dominio.

### Problema Resuelto
La rúbrica R03 (Criterio 2: Hibernate y PostgreSQL) evalúa la evolución del prototipo hacia un ORM profesional. Para ello era indispensable mapear las tablas existentes de PostgreSQL (schema.sql) a entidades Java con relaciones @OneToMany y @ManyToOne, sin acoplar las clases del dominio ventas.domain a anotaciones JPA.

### Solución Implementada
- **pom.xml:** Dependencia oficial `org.hibernate:hibernate-core:5.6.15.Final`.
- **HibernateUtil.java:** Creación programática de SessionFactory leyendo variables de entorno seguras (`DB_URL`, `DB_USER`, `DB_PASSWORD`).
- **Entidades JPA:** ProductoEntity, VentaEntity y DetalleVentaEntity mapeando llaves primarias, foráneas, restricciones de longitud y precisión decimal (`NUMERIC(12,2)` -> `BigDecimal`).
- **HibernateSessionTest.java:** Prueba unitaria validando apertura y cierre de sesiones de Hibernate.

### Evidencias en GitHub
- **Rama:** `feature/c02-hibernate-entidades-mapeo`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/46
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/47
- **Revisor Asignado:** Alejandro Lezama Aguilar