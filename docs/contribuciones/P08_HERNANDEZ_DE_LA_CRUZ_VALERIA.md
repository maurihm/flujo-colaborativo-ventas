# Evidencia de Contribución P08
**Integrante:** Valeria Hernández De la Cruz
**Módulo y Componente Asignado:** M10

## Resumen de la Contribución
Adaptador de Persistencia de Ventas JDBC
Implementé VentaJdbcRepository como adaptador de salida del puerto VentaRepository, mapeando la entidad Venta y Cliente hacia la tabla relacional venta de PostgreSQL mediante JDBC y PreparedStatement.

### Problema Resuelto
Permitir que el caso de uso de ventas persista los encabezados de venta y consulte ventas previas sin exponer conceptos de infraestructura hacia el dominio.

### Solución Implementada
- VentaJdbcRepository: Mapeo explícito de campos del cliente y total de venta con try-with-resources.
- VentaJdbcRepositoryTest: Pruebas de integración validando persistencia y consulta exitosa y manejo de ausencia.

### Evidencias en GitHub
- **Rama:** `feature/jdbc-venta-repositorio`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/38
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/39
- **Revisor Asignado:** Alejandro Lezama Aguilar