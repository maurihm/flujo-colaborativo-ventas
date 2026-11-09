# Evidencia de Contribución C02
**Integrante:** Alejandro Lezama Aguilar  
**Módulo y Componente Asignado:** Corte 2 — Integración Swing con Persistencia Hibernate y Corrección EDT

## Resumen de la Contribución
Conecté el cliente de escritorio Swing con la persistencia relacional en PostgreSQL 16 utilizando los adaptadores de Hibernate (`ProductoHibernateRepository`, `VentaHibernateRepository`). Eliminé las consultas SQL directas de la capa de presentación delegando la carga del catálogo al método `listarTodos()` del repositorio y corregí la concurrencia del Event Dispatch Thread (EDT) en el controlador y el arranque de la aplicación.

### Problema Resuelto
En la evaluación de R03 se detectó que la interfaz gráfica aún arrancaba con JDBC, mantenía una sentencia SQL en la presentación y leía entradas del formulario dentro del hilo secundario de `SwingWorker`. Para subsanar esto, se integró el cliente directamente con Hibernate, se limpió la capa visual de dependencias SQL y se garantizó el acceso estricto a componentes gráficos en el EDT.

### Solución Implementada
- **`ProductoRepository.java`:** Incorporación del método `List<Producto> listarTodos()` en el puerto de salida del dominio.
- **`ProductoHibernateRepository.java`:** Implementación de `listarTodos()` mediante consulta HQL mapeada a entidades JPA sin SQL nativo.
- **`SwingApplication.java`:** Arranque inicializado con `ProductoHibernateRepository` y `VentaHibernateRepository`. Eliminación de `cargarProductosDesdeBD()` y de imports `java.sql.*`. Precarga de datos y construcción del modelo de tabla ejecutadas dentro de `SwingUtilities.invokeLater()`.
- **`VentaSwingController.java`:** Extracción de datos del formulario y armado del comando de registro dentro del hilo EDT antes de invocar `doInBackground()`.
- **`SwingPersistenciaIntegrationTest.java`:** Actualización de la prueba de integración para validar el ciclo de persistencia y catálogo en PostgreSQL mediante Hibernate.

### Evidencias en GitHub
- **Rama de Corrección:** `fix/c02-swing-hibernate-edt-integracion`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/54
- **Pull Request (PR):** [PENDIENTE_DE_CREAR]
- **Revisor Asignado:** Valeria Hernández De la Cruz
- **Registro Inicial C02:** Issue #44 | PR #45
