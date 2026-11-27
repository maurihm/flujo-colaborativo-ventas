# Evidencia de Contribución C04: Candidato de Liberación
**Integrante:** Mauricio Hernández Martínez  
**Módulos Integrados:** M01–M14 (Dominio, Aplicación, Persistencia Hibernate/PostgreSQL y API REST Spring Boot)

## Resumen de la Contribución
En el Corte 4 (C04 — Candidato de Liberación `v1.0-rc`), consolidé la verificación de calidad orientada a riesgos críticos del sistema mediante la suite de integración `CandidatoLiberacionRiesgosTest` y la elaboración de la matriz formal `docs/matriz-riesgos-pruebas.md`, validando las 90 pruebas automatizadas del proyecto (81 en backend con JUnit 5 y 9 en frontend con Jasmine/Karma) junto con el reporte de cobertura JaCoCo.

### Problema Resuelto
Contar con pruebas unitarias aisladas en memoria no basta para liberar una versión candidata: en fronteras reales pueden presentarse discrepancias entre repositorios en memoria y transacciones Hibernate sobre PostgreSQL (asignación de identificadores, longitud de columnas `VARCHAR`, reversión atómica de existencias ante falta de stock o exposición accidental de detalles internos en errores 500). Se diseñaron pruebas específicas para cubrir estos escenarios críticos sobre base de datos real y respuestas RFC 9457.

### Solución Implementada y Evidencias
- **Suite de Riesgos Críticos (`CandidatoLiberacionRiesgosTest.java`):**
  - *Riesgo R-01 (Venta E2E sobre PostgreSQL):* Verifica `POST /ventas` con correo institucional extenso (>20 caracteres), generación de ID `VNT-*`, estado `CONFIRMADA` y descuento exacto de inventario en PostgreSQL.
  - *Riesgo R-02 (Atomicidad y Rollback ante Stock Insuficiente):* Verifica que una partida sin inventario suficiente aborte toda la transacción preservando intacto el stock de las partidas previas y devuelva `409 Conflict` con estructura RFC 9457 `ProblemDetail`.
  - *Riesgo R-03 (Invariantes y Sanitización de Errores):* Verifica validación de `Venta.asignarId` y respuestas `400`/`500` en `ApiExceptionHandler` sin filtrar detalles sensibles.
- **Matriz de Riesgos (`docs/matriz-riesgos-pruebas.md`):** Trazabilidad entre los 7 riesgos relevantes del candidato `v1.0-rc`, las suites automatizadas y los límites declarados.
- **Cobertura JaCoCo:** Ejecución completa de 81 pruebas JUnit 5 + 9 pruebas Karma superando el 80% global de cobertura de instrucciones.

### Declaración de Estados de Verificación
- **VERIFICADO:** Ejecución de 81 pruebas de backend (`BUILD SUCCESS`) sobre PostgreSQL 16, 9 pruebas unitarias de Angular en `ChromeHeadless` (`TOTAL: 9 SUCCESS`), reporte de cobertura JaCoCo >80% y validación automática en el pipeline de GitHub Actions.
- **NO_VERIFICADO:** Pruebas visuales interactivas sobre ventanas `JFrame` del cliente de escritorio heredado (declarado como límite en `docs/matriz-riesgos-pruebas.md`).
- **PENDIENTE:** Ninguno.

### Trazabilidad en GitHub
- **Rama:** `feature/c04-pruebas-cobertura-riesgos`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/84 (#84)
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/85 (#85)
- **Revisor Asignado:** Axel Uriel García Loyo (@kaos-gl)
