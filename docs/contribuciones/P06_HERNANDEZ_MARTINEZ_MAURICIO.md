# Evidencia de Participación - Patrones de Diseño (P06)

**Integrante:** HERNANDEZ MARTINEZ MAURICIO
**Rol/Patrón asignado:** Observer (Notificar inventario y bitácora al confirmar venta)

## Contexto de la Tarea (Issue / PR)
- **Issue:** Observer: notificar inventario al confirmar venta
- **Rama:** `feature/observer`
- **Pull Request:** Feature: Patrón Observer para eventos de dominio

## Implementación Técnica (Patrón Observer)
### Problema que resuelve
El servicio `ConfirmarVentaService` tenía la responsabilidad directa de descontar la existencia del inventario, acoplándolo a esa regla y dificultando la incorporación de nuevas reacciones a una venta (como registrar en bitácora) sin modificar la clase original (violando el principio Abierto/Cerrado).

### Solución aplicada (Patrón Observer)
Se desacopló la emisión del evento (cuando se confirma una venta) de sus reacciones.
- Se implementó la interfaz `VentaObserver` y el evento `VentaConfirmada`.
- Se crearon observadores específicos: `ActualizadorInventario` y `BitacoraVenta`.
- El servicio `ConfirmarVentaService` ahora simplemente notifica a los observadores suscritos mediante el método `agregarObservador` iterando sobre una lista.

### Archivos creados / modificados
1. `src/main/java/ventas/domain/VentaObserver.java`
2. `src/main/java/ventas/domain/VentaConfirmada.java`
3. `src/main/java/ventas/application/ActualizadorInventario.java`
4. `src/main/java/ventas/application/BitacoraVenta.java`
5. `src/main/java/ventas/application/ConfirmarVentaService.java`
6. `src/test/java/ventas/application/ObserverTest.java`

## Pruebas (Comprobación de la variación)
Se creó la prueba unitaria `ObserverTest` en la cual se inyectan los observadores (`ActualizadorInventario` y `BitacoraVenta`) a un `ConfirmarVentaService`. 
Al llamar a `confirmar()`, se verifica que:
1. El estado de la venta cambia a `PAGADA`.
2. Las existencias del producto asociado disminuyen correspondientemente, comprobando la propagación correcta del evento sin un acoplamiento duro.

## Costos y Limitaciones de la Decisión
- **Costo:** Se añaden varias clases y una interfaz adicional al proyecto, lo que aumenta ligeramente la complejidad estructural (y la cantidad de archivos a mantener) para una operación que antes se resolvía en una sola línea.
- **Limitación:** El orden en que se ejecutan los observadores no está garantizado. Si un observador falla silenciosamente o lanza una excepción no controlada, podría interrumpir la ejecución de los demás observadores.
