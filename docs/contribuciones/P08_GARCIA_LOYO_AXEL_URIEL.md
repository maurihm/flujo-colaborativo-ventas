# Evidencia de Contribución P08
**Integrante:** Axel Uriel Garcia Loyo
**Módulo y Componente Asignado:** M11 Inyección de Fallos y Demostración de Rollback

## Resumen de la Contribución
Diseñé e implementé la prueba de integración negativa `TransaccionRollbackTest` para demostrar de manera reproducible la garantía de atomicidad y reversión transaccional en PostgreSQL ante fallos de integridad.

### Problema Resuelto
Comprobar de forma científica y verificable que un fallo intermedio durante el registro de la venta ejecuta `rollback()`, evitando estados corruptos o inventarios descontados erróneamente.

### Solución Implementada
- Inyección de violación de integridad referencial (producto no persistido en BD).
- Verificación exhaustiva de 3 puntos: ausencia de encabezado, ausencia de detalles huérfanos e inventario de productos intacto.

### Evidencias en GitHub
- **Rama:** `feature/transaccion-rollback-consistencia`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/42
- **Pull Request (PR):** 