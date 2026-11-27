# Notas de Versión — `v1.0-rc` (`ventas-tcsw-1.0.0-rc.jar`)

**Fecha:** 26 de noviembre de 2026  
**Etiqueta Git:** `v1.0-rc`  
**Rama Base:** `main`  
**Artefacto Principal:** `target/ventas-tcsw-1.0.0-rc.jar`

## Capacidades Incluidas (M01–M14)
- **Núcleo de Dominio y Arquitectura Hexagonal:** Entidades puras (`Producto`, `Venta`, `DetalleVenta`, `Cliente`, `Dinero`), casos de uso y patrones GoF verificados con ArchUnit.
- **API REST Spring Boot (OpenAPI 3.1.0):** Endpoints de catálogo y ventas con respuestas estructuradas bajo RFC 9457 (`ProblemDetail`).
- **Cliente Web Angular 20:** Catálogo interactivo, alta reactiva con validaciones accesibles, carrito de ventas y manejo explícito de los 5 estados asíncronos.
- **Contenedorización Full-Stack:** Imágenes multi-etapa (`Dockerfile.api`, `Dockerfile.frontend` con Nginx reverse proxy `/api/`) y orquestación `docker-compose.yml` con healthchecks.
- **Integración Continua:** Pipeline en GitHub Actions (`.github/workflows/ci.yml`) ejecutando 90 pruebas automatizadas, smoke test E2E en Docker y publicación de artefactos.

## Mejoras y Defectos Corregidos respecto a `v0.3-web`
- Integración transaccional completa de `POST /ventas` sobre PostgreSQL 16 y Hibernate: generación de identificador `VNT-*`, actualización idempotente del estado `PAGADA` y acotamiento de longitud de contacto (`VARCHAR(20)`).
- Respuesta controlada `409 Conflict` bajo RFC 9457 (`ProblemDetail`) con rollback atómico ante stock insuficiente.
- Empaquetado ejecutable (`repackage`) generando `target/ventas-tcsw-1.0.0-rc.jar`.
- Retiro del archivo residual `token.json` y ejecución del contenedor API con usuario sin privilegios (`appuser`).

## Artefactos Generados y Verificación
- `ventas-tcsw-1.0.0-rc.jar`: verificable mediante `mvn clean package` o descargable desde el workflow de GitHub Actions (`ventas-tcsw-jar`).
- Reporte de cobertura JaCoCo (`jacoco-coverage-report`) y bundle de producción Angular (`frontend-angular-dist`).

## Riesgos Residuales y Límites Conocidos
- Se mantiene `hibernate.hbm2ddl.auto=update` para compatibilidad del entorno de laboratorio; las migraciones versionadas con Flyway y la autenticación basada en tokens (JWT) se declaran como evolución futura posterior a `v1.0-rc`.
- Las vistas gráficas heredadas de escritorio Swing (`MainFrame`, `CatalogoPanel`, `SwingApplication`) requieren entorno gráfico interactivo y quedan fuera del contenedor web.
