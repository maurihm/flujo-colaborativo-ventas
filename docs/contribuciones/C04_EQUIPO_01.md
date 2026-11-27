# UNIVERSIDAD VERACRUZANA
## Tecnologías para la Construcción de Software (TCSW-19234)
# C04_EQUIPO_01
Evidencia de Equipo: Actividad C04 (Corte 4: Candidato de Liberación)

**Módulos Integrados:** M01–M14 (Núcleo Hexagonal, Patrones GoF, Swing, JDBC/Hibernate PostgreSQL 16, API REST Spring Boot, Cliente Angular, Contenedores y CI)
**Instrumento de Evaluación:** R05. Candidato de liberación (8.00 puntos)
**Repositorio Oficial:** https://github.com/maurihm/flujo-colaborativo-ventas
**Rama Oficial de Entrega:** `main`
**Etiqueta Oficial del Corte:** `v1.0-rc`
**Artefacto Ejecutable:** `target/ventas-tcsw-1.0.0-rc.jar`
**Integrantes (Equipo 01):**
1. Alejandro Lezama Aguilar
2. Valeria Hernández De la Cruz
3. Mauricio Hernández Martínez
4. Axel Uriel García Loyo

## 1. Índice de Evidencias por Criterio de la Rúbrica R05

| Criterio R05 | Puntaje | Estado | Evidencias en el Repositorio | Descripción y Justificación |
| :--- | :---: | :---: | :--- | :--- |
| **1. Integración** | **2.0 pts** | VERIFICADO | `frontend-angular/nginx.conf`<br>`ProductoRestController.java`<br>`VentaRestController.java`<br>`VentaHibernateRepository.java` | Recorrido vertical completo entre cliente Angular en Nginx (`/api/`), API Spring Boot y PostgreSQL 16: consulta, alta de productos, venta atómica con descuento de stock y errores controlados RFC 9457 (`400`, `404`, `409`). |
| **2. Pruebas** | **1.5 pts** | VERIFICADO | `src/test/java/**` (81 tests)<br>`CandidatoLiberacionRiesgosTest.java`<br>`frontend-angular/src/**/*.spec.ts` (9 tests)<br>`docs/matriz-riesgos-pruebas.md` | 90 pruebas automatizadas en verde (81 backend + 9 frontend), cobertura JaCoCo >80% y matriz formal de pruebas orientada a riesgos relevantes de liberación. |
| **3. Sonar** | **1.0 pt** | VERIFICADO | `sonar-project.properties`<br>`pom.xml` (JaCoCo + Sonar)<br>`docs/sonar-hallazgos.md` | Análisis estático reproducible ejecutado en SonarQube con Quality Gate **PASSED** y bitácora explícita de revisión y tratamiento de hallazgos. |
| **4. Integración continua** | **1.0 pt** | VERIFICADO | `.github/workflows/ci.yml`<br>Pestaña GitHub Actions | Flujo automatizado verificable con 3 jobs en verde (Backend + PostgreSQL 16, Frontend Headless + Build, y Despliegue E2E Docker Compose) y artefactos publicados. |
| **5. Documentación** | **1.0 pt** | VERIFICADO | `README.md`<br>`RELEASE_NOTES.md`<br>`docs/plan-de-reversa.md`<br>`docs/openapi.yaml` | Guía completa de arquitectura y despliegue `v1.0-rc`, notas de versión con alcance y límites declarados, y plan de reversa hacia `v0.3-web` ejecutado y comprobado. |
| **6. Reproducibilidad** | **1.0 pt** | VERIFICADO | `Dockerfile.api`<br>`Dockerfile.frontend`<br>`docker-compose.yml`<br>`.env.example` | Construcción multi-etapa determinista y ejecución limpia desde cero mediante `docker compose up -d --build` con healthchecks encadenados. |
| **7. Versión** | **0.5 pts** | VERIFICADO | Tag `v1.0-rc`<br>`pom.xml` (`1.0.0-rc`)<br>`package.json` (`1.0.0-rc`)<br>`ventas-tcsw-1.0.0-rc.jar` | Versionado congelado consistente en backend, frontend y contrato OpenAPI, artefacto JAR ejecutable identificado y etiqueta Git anotada `v1.0-rc` en `main`. |

### Declaración de Estados de Verificación
- **VERIFICADO:** Integración extremo a extremo en contenedores, 90 pruebas automatizadas (81 JUnit + 9 Karma), cobertura JaCoCo, Quality Gate PASSED en SonarQube con hallazgos tratados, pipeline CI en GitHub Actions en verde, documentación `README.md` y `RELEASE_NOTES.md`, ejecución real de reversa a `v0.3-web` y etiqueta `v1.0-rc`.
- **NO_VERIFICADO:** Despliegue en nube pública externa (restringido deliberadamente conforme a las reglas de seguridad de la guía C04).
- **PENDIENTE:** Migraciones incrementales con Flyway y autenticación de usuarios (registrados como evolución futura en `RELEASE_NOTES.md`).
