# Evidencia de Contribución P06
**Integrante:** Alejandro Lezama Aguilar
**Patrón Asignado:** Factory (Creacional)

## Resumen de la Contribución
Implementé el patrón de diseño **Factory** para centralizar y proteger la creación de las entidades `Venta` y `DetalleVenta`. 

### Problema Resuelto
Antes, los objetos de dominio se instanciaban con `new Venta()` directamente en los servicios de aplicación, lo cual permitía crear ventas en estado inconsistente (ej. sin cliente).

### Solución Implementada
Se crearon `VentaFactory` y `DetalleVentaFactory` con métodos estáticos que validan las invariantes del negocio antes de instanciar los objetos:
- `VentaFactory.crear(Cliente)` asegura que el cliente no sea nulo.
- `DetalleVentaFactory.crear(Producto, int)` asegura que el producto exista y la cantidad sea lógica (>0).
Además, se modificó `CrearVentaService` para que utilice el Factory, eliminando el acoplamiento a los constructores directos.

### Evidencias en GitHub
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/19
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/21 
- **Commits:**https://github.com/maurihm/flujo-colaborativo-ventas/pull/21/commits
  - `feat: implementar VentaFactory y DetalleVentaFactory`
  - `test: agregar pruebas unitarias para validación de invariantes en Factory`
