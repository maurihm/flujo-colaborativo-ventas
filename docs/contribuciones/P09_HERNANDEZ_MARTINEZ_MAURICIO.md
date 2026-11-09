# Evidencia de Contribución P09
**Integrante:** Mauricio Hernández Martínez  
**Módulo y Componente Asignado:** M12. API básica — Controlador REST de Ventas (`VentaRestController`) y DTOs de Transacción (`ItemVentaDto`, `CrearVentaRequestDto`, `VentaResponseDto`)

## Resumen de la Contribución
Implementé el adaptador primario web para el procesamiento de ventas mediante `POST /ventas` en `VentaRestController`, junto con los objetos de transferencia de datos de entrada y salida (`ItemVentaDto`, `CrearVentaRequestDto` y `VentaResponseDto`). El controlador valida declarativamente la carga útil con Bean Validation y delega la lógica transaccional de negocio a `VentaFacade` y `ProductoRepository`, preservando el aislamiento de la arquitectura hexagonal.

### Problema Resuelto
El prototipo requería exponer el flujo completo de creación y confirmación de una venta a través de HTTP REST cumpliendo con el contrato formal OpenAPI 3.1.0 (`docs/openapi.yaml`) y sin acoplar las entidades de dominio (`Venta`, `Cliente`, `Producto`, `DetalleVenta`) a anotaciones de serialización JSON ni al contenedor web de Spring Boot:
1. Validar que toda solicitud de venta incluya datos válidos del cliente (`@NotBlank`, `@Email`) y al menos una partida de producto con cantidad positiva (`@NotEmpty`, `@Valid`, `@Min(1)`).
2. Verificar la existencia de cada producto solicitado en el repositorio y propagar `NoSuchElementException` hacia `ApiExceptionHandler` para retornar un `404 Not Found` bajo RFC 9457 cuando un código no exista.
3. Orquestar la creación, persistencia y confirmación de la venta mediante `VentaFacade.procesarVentaCompleta(...)` y retornar `201 Created` con el encabezado `Location: /ventas/{id}` y el resumen en `VentaResponseDto`.

### Solución Implementada
- **`ItemVentaDto.java`:** DTO de partida de venta con restricciones `@NotNull` para `codigoProducto` y `@NotNull` / `@Min(1)` para `cantidad`.
- **`CrearVentaRequestDto.java`:** DTO de solicitud de venta con validaciones `@NotBlank` para `clienteNombre`, `@NotBlank` y `@Email` para `clienteEmail`, y validación en cascada (`@NotEmpty`, `@Valid`) sobre la lista de `items`.
- **`VentaResponseDto.java`:** DTO de respuesta desacoplado del dominio que transporta `id`, `cliente`, `total`, `estado` y `detallesCount`.
- **`VentaRestController.java`:** Controlador `@RestController` mapeado a `/ventas` que traduce el `CrearVentaRequestDto` en objetos del dominio, consulta los productos en `ProductoRepository`, invoca `VentaFacade.procesarVentaCompleta(...)` y construye la respuesta `201 Created` con su URI de ubicación.

### Evidencias en GitHub
- **Rama de Trabajo:** `feature/p09-venta-controller-adapter`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/60
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/61
- **Revisor Asignado:** Axel Uriel García Loyo
