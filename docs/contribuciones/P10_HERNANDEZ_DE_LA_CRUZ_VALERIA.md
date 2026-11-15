# Evidencia de Contribución P10
**Integrante:** Valeria Hernández De la Cruz  
**Módulo y Componente Asignado:** M13. Angular básico — Catálogo de Productos, Detalle de Producto y Manejo de Estados Asíncronos de UI

## Resumen de la Contribución
Diseñé e implementé la capa de vistas para el catálogo de productos bajo un patrón de diseño de componentes separados (Smart/Dumb components) en Angular. Desarrollé el `ProductosPageComponent` como componente coordinador encargado de consumir la API, y el `ProductoListComponent` como componente de presentación puramente visual. Además, integré una gestión reactiva y explícita de los cinco estados de la interfaz de usuario (`idle`, `loading`, `empty`, `success`, `error`), garantizando en todo momento la accesibilidad semántica para lectores de pantalla. Finalmente, dejé preparada la estructura y navegación paramétrica para la vista individual mediante `ProductoDetailComponent`.

### Problema Resuelto
La aplicación cliente necesitaba una forma eficiente e intuitiva de mostrar el catálogo de productos proveniente del backend, resolviendo los siguientes retos técnicos:
1. Era indispensable separar la lógica de consumo de servicios HTTP de la lógica de renderizado HTML para cumplir con las buenas prácticas de la arquitectura de Angular (separación de responsabilidades).
2. Se requería evitar pantallas en blanco o "congeladas" durante los tiempos de respuesta del servidor, así como fallas silenciosas en caso de que la API de Spring Boot devolviera errores o no hubiera conexión de red.
3. Se debía garantizar el cumplimiento de estándares de accesibilidad web (WCAG), asegurando que los usuarios con lectores de pantalla fueran notificados dinámicamente de los cambios de estado (cargas, errores, o tablas vacías).
4. Faltaba habilitar el ruteo interno hacia el detalle de un producto específico capturando parámetros dinámicos en la URL (`/productos/:codigo`).

### Solución Implementada
- **`productos-page.component.ts` y `.html`:** Componente coordinador (Smart Component) que inyecta `ProductosService` y `Router`. Gestiona la suscripción HTTP al catálogo y controla dinámicamente la máquina de estados de la UI, delegando las directivas visuales según el resultado del servidor.
- **`producto-list.component.ts` y `.html`:** Componente de presentación (Dumb Component) totalmente desacoplado. Recibe el arreglo de productos renderizado a través del decorador `@Input()` y comunica las acciones del usuario (ver detalle, agregar al carrito) hacia el componente padre mediante decoradores `@Output()` y `EventEmitter`.
- **Manejo Estricto de Accesibilidad (A11y):** Inclusión de atributos estructurales en las plantillas HTML como `role="status"` y `aria-live="polite"` para los *spinners* de carga, `role="alert"` y `aria-live="assertive"` para los mensajes de error RFC 9457, y etiquetas semánticas `aria-label` en los botones y tablas de datos.
- **`producto-detail.component.ts`:** Base del componente de vista detallada preparado para interceptar el código de producto mediante inyección de `ActivatedRoute`.

### Evidencias en GitHub
- **Rama de Trabajo:** `feature/p10-catalogo-productos-estados-ui`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/66
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/67
- **Revisor Asignado:** Alejandro Lezama Aguilar