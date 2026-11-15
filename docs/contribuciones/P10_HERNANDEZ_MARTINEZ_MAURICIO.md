# Evidencia de Contribución P10
**Integrante:** Mauricio Hernández Martínez  
**Módulo y Componente Asignado:** M13. Angular básico — Formulario Reactivo de Productos, Carrito de Compras y Servicio de Registro de Ventas

## Resumen de la Contribución
Diseñé e implementé el flujo transaccional de captura y registro para el cliente Angular del prototipo de ventas. Desarrollé el componente de alta de productos `ProductoFormComponent` utilizando formularios reactivos (`ReactiveFormsModule`) con validaciones sincronizadas con las restricciones de OpenAPI y accesibilidad semántica. Asimismo, modelé las estructuras de datos de transacciones comerciales en `venta.model.ts`, creé el servicio `VentasService` inyectable para procesar órdenes de compra mediante peticiones HTTP `POST /ventas`, y construí el componente `CarritoVentasComponent` que permite gestionar las partidas en memoria, asociar los datos del cliente y concretar la venta con captura de errores RFC 9457.

### Problema Resuelto
El frontend contaba con capacidades de consulta pero carecía de mecanismos para ingresar y persistir información en el sistema:
1. Se requería un formulario de captura para nuevos productos que impidiera el envío de datos inválidos (código no numérico o vacío, precio menor o igual a cero, nombres en blanco) directamente en el navegador antes de emitir solicitudes a la API.
2. Era necesario asegurar accesibilidad WCAG en los mensajes de validación, evitando notificar fallos únicamente mediante bordes rojos o cambios de color.
3. Se debía implementar la lógica de punto de venta: selección de artículos desde el catálogo, cálculo reactivo de subtotales por partida, total general y validación del formato de correo electrónico y datos del comprador.
4. Faltaba conectar la interfaz de compras con el endpoint transaccional `POST /ventas` del backend Spring Boot, asegurando una adecuada retroalimentación con el identificador generado o con el detalle estructurado de errores RFC 9457 ante excepciones de negocio o existencias insuficientes.

### Solución Implementada
- **`producto-form.component.ts`, `.html` y `.css`:** Formulario reactivo que utiliza `FormBuilder` para estructurar los controles `codigo`, `nombre`, `precio`, `existencia` y `categoria`. Integra validadores síncronos (`Validators.required`, `Validators.min`, `Validators.minLength`), asocia mensajes descriptivos mediante `aria-describedby` y muestra alertas dinámicas accesibles (`role="alert"` y `role="status"`).
- **`venta.model.ts`:** Interfaces TypeScript (`CrearVentaRequest`, `ItemVenta`, `VentaResponse`, `ItemCarrito`) que modelan fielmente el contrato de compraventa sin dependencias internas de persistencia.
- **`ventas.service.ts`:** Servicio HTTP inyectable en raíz que encapsula la llamada `POST /ventas` retornando un `Observable<VentaResponse>` y canalizando excepciones a objetos `ProblemDetail`.
- **`carrito-ventas.component.ts`, `.html` y `.css`:** Componente integral de compras que consume el catálogo disponible, administra las partidas de la orden, calcula el monto total a pagar y envía la transacción al servidor, mostrando un resumen con folio en caso de éxito o mensajes claros ante errores.
- **`producto-detail.component.ts`:** Implementación del componente de detalle que recupera dinámicamente el código del producto desde la ruta activa (`ActivatedRoute`) y consulta su información al backend.

### Evidencias en GitHub
- **Rama de Trabajo:** `feature/p10-formulario-reactivo-ventas`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/68
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/69
- **Revisor Asignado:** Axel Uriel García Loyo
