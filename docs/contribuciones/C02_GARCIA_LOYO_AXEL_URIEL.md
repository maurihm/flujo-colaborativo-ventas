# Evidencia de Contribución C02
**Integrante:** Axel Uriel García Loyo
**Módulo y Componente Asignado:** Corte 2 Reversión Transaccional Hibernate, Suite de Retest y Etiquetado v0.2-escritorio

## Resumen de la Contribución
Implementé la prueba de integración negativa en Hibernate para comprobar el comportamiento de reversión atómica (rollback) ante fallos de integridad referencial, verifiqué la ejecución completa de la suite de 64 pruebas automatizadas con Maven, elaboré el indice maestro `C02_EQUIPO_01.md` y etiqueté el corte oficial con la etiqueta `v0.2-escritorio`.

### Problema Resuelto
El instrumento R03 (Criterios 3 y 4) exige demostrar que los errores se controlan y que el estado de la base de datos permanece 100% limpio sin registros parciales tras un fallo transaccional. Además, se requiere un corte formal versionado mediante Git Tag para la entrega oficial.

### Solución Implementada
- **TransaccionHibernateRollbackTest.java:** Inyección de fallo en detalle secundario; aserción de ausencia de encabezado en venta y verificación de que el stock original en producto permanece intacto.
- **docs/contribuciones/C02_EQUIPO_01.md:** Índice grupal mapeando cada evidencia a los criterios de la rúbrica R03.
- **Etiqueta Git v0.2-escritorio:** Creación y publicación del tag en el commit final de `main`.

### Evidencias en GitHub
- **Rama:** `feature/c02-rollback-etiqueta-entrega`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/50
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/51
- **Etiqueta Oficial:** `v0.2-escritorio`
- **Revisor Asignado:** Mauricio Hernández Martínez
