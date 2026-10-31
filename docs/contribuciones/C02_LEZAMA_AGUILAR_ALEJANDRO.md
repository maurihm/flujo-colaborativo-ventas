# Evidencia de Contribución C02
**Integrante:** Alejandro Lezama Aguilar  
**Módulo y Componente Asignado:** Corte 2 — Integración Swing con Persistencia PostgreSQL y Retest

## Resumen de la Contribución
Conecté el cliente de escritorio Swing con la persistencia relacional en PostgreSQL 16, sustituyendo los repositorios en memoria por adaptadores JDBC reales (`ProductoJdbcRepository`, `VentaJdbcRepository`). Implementé la carga automática de catálogo desde la base de datos al iniciar la interfaz gráfica y desarrollé la prueba de integración de persistencia de escritorio.

### Problema Resuelto
En P07 el cliente Swing dependía de `InMemoryProductoRepository`, por lo que todos los productos y ventas se perdían al cerrar la ventana. Para cumplir con el producto exacto de C02 (*"Cliente Swing conectado al adaptador PostgreSQL con retest"*), se requería acoplar la aplicación a la base de datos preservando la arquitectura hexagonal y la reactividad del EDT.

### Solución Implementada
- **`SwingApplication.java`:** Ensamblado con `ProductoJdbcRepository` y `VentaJdbcRepository`. Implementación del método de sincronización inicial `cargarProductosDesdeBD()` para poblar `ProductoTableModel`.
- **`SwingPersistenciaIntegrationTest.java`:** Prueba automatizada que valida el ciclo completo: registro de producto desde `VentaSwingController`, persistencia física en PostgreSQL y reflejo en el modelo de tabla Swing.
- **Demostración de Persistencia:** Ejecución de la aplicación, captura de producto en la interfaz, cierre forzado y comprobación de que el registro continúa disponible tras reiniciar.

### Evidencias en GitHub
- **Rama:** `feature/c02-swing-postgresql-integracion`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/44
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/45
- **Revisor Asignado:** Valeria Hernández De la Cruz
