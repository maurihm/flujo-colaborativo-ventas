# Evidencia de Contribución C04: Candidato de Liberación
**Integrante:** Alejandro Lezama Aguilar  
**Módulos Integrados:** M10–M14 (Persistencia PostgreSQL/Hibernate, API REST Spring Boot, Cliente Angular y Contenedores Docker)

## Resumen de la Contribución
Para el Corte 4 (C04 — Candidato de Liberación `v1.0-rc`), diseñé e implementé la contenedorización multi-etapa de toda la plataforma (`Dockerfile.api`, `Dockerfile.frontend` con Nginx reverse proxy y `docker-compose.yml`), configuré el empaquetado ejecutable `ventas-tcsw-1.0.0-rc.jar` en Maven y resolví la integración transaccional de `POST /ventas` al operar sobre PostgreSQL real con Hibernate.

### Problema Resuelto
En el cierre del Corte 3 (`v0.3-web`), el repositorio solo contenedorizaba PostgreSQL, carecía del goal `repackage` en `spring-boot-maven-plugin` para producir un JAR autónomo y el cliente Angular dependía del proxy de desarrollo de `ng serve`. Además, al probar `POST /ventas` extremo a extremo contra Hibernate y PostgreSQL se detectó que las ventas nuevas carecían de identificador asignado antes de `session.save()` y que la confirmación reintentaba insertar el mismo encabezado en lugar de actualizar su estado a `PAGADA`. Se corrigieron estas fronteras sin alterar las invariantes del dominio.

### Solución Implementada y Evidencias
- **Empaquetado y Versionado (`pom.xml`, `package.json`, `docs/openapi.yaml`):** Fijación de versión `1.0.0-rc` y generación del artefacto ejecutable `target/ventas-tcsw-1.0.0-rc.jar`.
- **Integración Transaccional de Ventas (`Venta`, `VentaFacade`, `VentaHibernateRepository`, `ApiExceptionHandler`):** Asignación automática de ID al persistir en BD, actualización limpia a `PAGADA`, acotamiento de longitud de contacto y respuesta controlada `409 Conflict` bajo RFC 9457 ante stock insuficiente.
- **Despliegue Reproducible (`Dockerfile.api`, `Dockerfile.frontend`, `nginx.conf`, `docker-compose.yml`, `.env.example`):** Orquestación con `healthcheck` de PostgreSQL 16, API Spring Boot con usuario sin privilegios y Nginx sirviendo el bundle de Angular 20 y enrutando `/api/` hacia `api:8080`.

### Declaración de Estados de Verificación
- **VERIFICADO:** Construcción limpia de imágenes Docker, artefacto ejecutable `ventas-tcsw-1.0.0-rc.jar`, recorrido vertical completo (`GET/POST /productos`, `POST /ventas` con descuento atómico de inventario, errores 400/404/409 RFC 9457, proxy Nginx `/api/`) y suite completa de 78 pruebas JUnit en verde.
- **NO_VERIFICADO:** Ninguno.
- **PENDIENTE:** Ninguno.

### Trazabilidad en GitHub
- **Rama:** `feature/c04-integracion-docker-reproducibilidad`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/80 (#80)
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/81 (#81)
- **Revisora Asignada:** Valeria Hernández De la Cruz (@valux0)
