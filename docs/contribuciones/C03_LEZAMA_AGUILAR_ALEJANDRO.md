# Evidencia de Contribución C03: Integración Web
**Integrante:** Alejandro Lezama Aguilar  
**Módulos Integrados:** M12 (API REST) y M13 (Cliente Web)

## Resumen de la Contribución
Para el Corte 3 (C03), integré y consolidé los endpoints REST del catálogo en Spring Boot (`ProductoRestController`) conectándolos con la persistencia relacional en PostgreSQL 16 mediante Hibernate (`ProductoHibernateRepository`, `VentaHibernateRepository`) en `RestApplication.java`. Verifiqué la estricta concordancia contra el contrato formal OpenAPI 3.1.0 (`docs/openapi.yaml`), el cumplimiento de las cabeceras HTTP RFC 9110 (`Content-Type: application/json`) y la serialización JSON RFC 8259 sin exponer entidades internas.

### Problema Resuelto
En la fase previa de desarrollo (P09/P10), la API REST operaba con adaptadores en memoria (`InMemoryProductoRepository`), lo que impedía que los nuevos productos registrados por el cliente web o por peticiones HTTP persistieran en la base de datos relacional. Era indispensable integrar los adaptadores de Hibernate en la configuración de Spring Boot para satisfacer el criterio estricto de persistencia de la Rúbrica R04, asegurando que las altas y consultas se reflejen directamente en PostgreSQL sin romper el contrato OpenAPI ni la suite de pruebas automatizadas.

### Solución Implementada y Evidencias
- **Conexión a PostgreSQL en `RestApplication.java`:** Reemplazo de repositorios `InMemory` por `@Bean` con `ProductoHibernateRepository` y `VentaHibernateRepository`.
- **Contrato OpenAPI (`docs/openapi.yaml`):** Validación exhaustiva de tipos, campos obligatorios (`codigo`, `nombre`, `precio`, `existencia`) y respuestas HTTP semánticas.
- **Endpoints Funcionales en Spring Boot:**
  - `GET /productos`: Retorna la lista completa del inventario desde PostgreSQL con código `200 OK`.
  - `GET /productos/{codigo}`: Retorna el detalle del producto solicitado o `404 Not Found` en formato RFC 9457 `ProblemDetail`.
  - `POST /productos`: Registra un nuevo producto persistiendo en la base de datos y retornando `201 Created` con cabecera `Location`.
- **Pruebas de Integración:** Ejecución exitosa de `ProductoRestControllerTest` (4 pruebas con MockMvc en verde) y suite completa de 78 pruebas JUnit pasando sin fallos.

### Trazabilidad en GitHub
- **Rama:** `feature/c03-api-contrato-catalogo-endpoints`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/72 (#72)
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/73 (#73)
- **Revisora Asignada:** Valeria Hernández De la Cruz
