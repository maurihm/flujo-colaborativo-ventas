# Sistema de Ventas TCSW (`ventas-tcsw`) — Candidato de Liberación `v1.0-rc`

Sistema punto de venta desarrollado bajo **Arquitectura Hexagonal** con núcleo de dominio en Java, patrones GoF, persistencia relacional en PostgreSQL 16 con Hibernate, API REST en Spring Boot, cliente web en Angular 20 servido mediante Nginx, contenedorización multi-etapa con Docker Compose e Integración Continua en GitHub Actions.

## Versiones Congeladas (`1.0.0-rc`)

| Componente | Tecnología / Versión |
| :--- | :--- |
| Backend / Runtime | Java 11 (Eclipse Temurin / OpenJDK), Spring Boot `2.7.18` |
| Persistencia | Hibernate `5.6.15.Final`, PostgreSQL `16-alpine` |
| Construcción y Pruebas | Maven `3.9+`, JUnit 5, ArchUnit, JaCoCo `0.8.12` |
| Frontend Web | Angular `20.3`, Node.js `22`, Nginx `1.27-alpine` |
| Contenedores y CI | Docker / Docker Compose, GitHub Actions |

## Opción A: Despliegue Rápido desde Entorno Limpio (Docker Compose)

```bash
git clone https://github.com/maurihm/flujo-colaborativo-ventas.git
cd flujo-colaborativo-ventas
git checkout v1.0-rc
docker compose up -d --build
docker compose ps
curl -i http://localhost:4200/api/productos
```

- **Cliente Web Angular (Nginx):** `http://localhost:4200`
- **API REST Spring Boot:** `http://localhost:8080` (accesible también vía proxy en `http://localhost:4200/api/`)
- **Base de Datos PostgreSQL 16:** puerto `5432` (configurable vía `.env`, ver `.env.example`)

## Opción B: Ejecución Manual de Desarrollo y Suite de Pruebas (90 tests)

```bash
# 1. Backend: 81 pruebas (JUnit 5 + ArchUnit) + reporte JaCoCo + fat JAR ejecutable
mvn clean package

# 2. Frontend: 9 pruebas unitarias en ChromeHeadless + build de producción
cd frontend-angular
npm ci
npm test -- --watch=false --browsers=ChromeHeadless
npm run build -- --configuration production
```

## Documentación Técnica y Evidencias del Candidato
- **Notas de Versión:** [`RELEASE_NOTES.md`](RELEASE_NOTES.md)
- **Contrato OpenAPI 3.1.0:** [`docs/openapi.yaml`](docs/openapi.yaml)
- **Matriz de Riesgos y Pruebas:** [`docs/matriz-riesgos-pruebas.md`](docs/matriz-riesgos-pruebas.md)
- **Análisis SonarQube y Hallazgos:** [`docs/sonar-hallazgos.md`](docs/sonar-hallazgos.md)
- **Plan de Reversa Probado:** [`docs/plan-de-reversa.md`](docs/plan-de-reversa.md)
- **Índice Consolidado del Equipo (C04):** [`docs/contribuciones/C04_EQUIPO_01.md`](docs/contribuciones/C04_EQUIPO_01.md)
