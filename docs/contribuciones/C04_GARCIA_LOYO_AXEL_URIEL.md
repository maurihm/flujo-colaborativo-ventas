# Evidencia de Contribución C04: Candidato de Liberación
**Integrante:** Axel Uriel García Loyo  
**Módulos Integrados:** M01–M14 (Calidad Estática SonarQube, Documentación Operativa, Plan de Reversa y Liberación `v1.0-rc`)

## Resumen de la Contribución
En el Corte 4 (C04 — Candidato de Liberación `v1.0-rc`), configuré el análisis estático reproducible en SonarQube con importación de cobertura JaCoCo (`sonar-project.properties`), documenté el tratamiento formal de hallazgos (`docs/sonar-hallazgos.md`), actualicé la documentación operativa (`README.md`, `RELEASE_NOTES.md`), ejecuté y comprobé el plan de reversa real hacia `v0.3-web` (`docs/plan-de-reversa.md`), consolidé el índice grupal (`docs/contribuciones/C04_EQUIPO_01.md`) y publiqué la etiqueta oficial `v1.0-rc`.

### Problema Resuelto
Antes del cierre del candidato de liberación faltaba formalizar el análisis estático reproducible en el repositorio, documentar la decisión técnica sobre hallazgos de seguridad y cobertura, actualizar el `README.md` (que aún correspondía a fases anteriores), redactar las notas de versión y demostrar en la práctica que el sistema puede revertirse a `v0.3-web` sin pérdida de datos.

### Solución Implementada y Evidencias
- **Análisis SonarQube (`sonar-project.properties` y `docs/sonar-hallazgos.md`):** Configuración sin credenciales embebidas, ejecución con reporte XML de JaCoCo, Quality Gate **PASSED** (0 Bugs, 0 Vulnerabilidades, 0 Code Smells) y registro de tratamiento de los 4 hallazgos principales.
- **Documentación Operativa (`README.md` y `RELEASE_NOTES.md`):** Guía integral de arquitectura, versiones congeladas `1.0.0-rc`, despliegue con Docker Compose, ejecución de las 90 pruebas y notas de versión con alcance, correcciones y límites conocidos.
- **Plan de Reversa (`docs/plan-de-reversa.md`):** Ejecución real de respaldo con `pg_dump`, retorno a `v0.3-web` verificando `HTTP/1.1 200 OK` y restauración limpia a `v1.0-rc`.
- **Trazabilidad y Cierre (`C04_EQUIPO_01.md` y tag `v1.0-rc`):** Revisión y aprobación del PR #85 de Mauricio Hernández, consolidación del índice de los 7 criterios de R05 y publicación de la etiqueta anotada `v1.0-rc` en `main`.

### Declaración de Estados de Verificación
- **VERIFICADO:** Quality Gate PASSED en SonarQube con cobertura JaCoCo importada, documentación operativa actualizada (`README.md`, `RELEASE_NOTES.md`), ejecución real del plan de reversa hacia `v0.3-web`, índice consolidado `C04_EQUIPO_01.md` y etiqueta `v1.0-rc` con CI en verde.
- **NO_VERIFICADO:** Despliegue en nube pública de terceros (omitido conforme a las restricciones de seguridad de C04).
- **PENDIENTE:** Ninguno.

### Trazabilidad en GitHub
- **Rama:** `feature/c04-sonar-docs-reversa-release`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/86 (#86)
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/87 (#87)
- **Revisor Asignado:** Mauricio Hernández Martínez (@maurihm)
