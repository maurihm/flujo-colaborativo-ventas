# Evidencia de Contribución P08

**Integrante:** Mauricio Hernández Martínez  
**Módulo y Componente Asignado:** M11 - Transaccionalidad Atómica y Actualización de Inventario

## Resumen de la Contribución
Implementé la coordinación transaccional de negocio en `VentaJdbcRepository`, gestionando el ciclo de vida de la conexión con `autoCommit(false)` para confirmar atómicamente el encabezado de venta, sus detalles y el descuento de inventario mediante `commit()`.

### Problema Resuelto
Evitar que una venta parcial quede registrada si fallara la inserción de algún detalle o la actualización del stock, protegiendo la consistencia de los datos.

### Solución Implementada
- Delimitación de frontera transaccional en `VentaJdbcRepository`.
- Descuento seguro de inventario en `producto` con condición atómica (`WHERE existencia >= ?`).
- `TransaccionVentaExitosaTest`: Comprobación de persistencia íntegra de venta, detalles y existencias actualizadas.

### Evidencias en GitHub
- **Rama:** `feature/transaccion-venta-detalles`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/40
- **Pull Request (PR):** [PEGAR LINK DEL PR AQUÍ]
- **Revisor Asignado:** Axel Uriel García Loyo
