# Evidencia de Contribución C04: Candidato de Liberación
**Integrante:** Valeria Hernández De la Cruz
**Módulos Integrados:** M12–M14 (API REST, Cliente Angular, Contenedores e Integración Continua)
## Resumen de la Contribución
En el Corte 4 (C04 — Candidato de Liberación `v1.0-rc`), diseñé e implementé el pipeline de Integración
Continua en GitHub Actions (`.github/workflows/ci.yml`), automatizando en un entorno efímero limpio la
verificación de la base de datos PostgreSQL 16, las pruebas y cobertura JaCoCo del backend, las pruebas
unitarias y compilación productiva de Angular, la validación extremo a extremo en Docker Compose y la
publicación de artefactos de liberación.
### Problema Resuelto
Hasta el Corte 3 las pruebas y compilaciones se ejecutaban manualmente en las computadoras locales de
cada integrante, lo que exponía al proyecto a diferencias de entorno (puertos ocupados, versiones de
Node/JDK o estado previo en PostgreSQL). Con el pipeline automatizado cada Pull Request, push a `main`
y etiqueta de versión se valida de forma objetiva y reproducible sin intervención humana.
### Solución Implementada y Evidencias
- **Job `backend-build-test`:** Levanta un servicio `postgres:16-alpine`, aplica
`src/main/resources/schema.sql`, ejecuta toda la suite JUnit 5 + ArchUnit bajo JDK 11 con `xvfb-run` y
publica `ventas-tcsw-1.0.0-rc.jar` junto con el reporte JaCoCo.
- **Job `frontend-build-test`:** Instala dependencias deterministas con `npm ci` en Node.js 22, ejecuta
las 9 pruebas unitarias en `ChromeHeadless` y compila el bundle productivo `dist/frontend-
angular/browser`.
- **Job `docker-compose-e2e`:** Construye las imágenes Docker del candidato y ejecuta un smoke test
extremo a extremo (alta de producto, venta transaccional y verificación de descuento de stock en
PostgreSQL a través de Nginx `/api/`).
### Declaración de Estados de Verificación
- **VERIFICADO:** Ejecución exitosa de los 3 jobs en GitHub Actions en PR y en `main`, más generación
de los 3 artefactos descargables.
- **NO_VERIFICADO:** Ninguno.
- **PENDIENTE:** Ninguno.
### Trazabilidad en GitHub
- **Rama:** `feature/c04-ci-github-actions`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/82 
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/83 
- **Revisor Asignado:** Alejandro Lezama Aguilar (@Lez267)