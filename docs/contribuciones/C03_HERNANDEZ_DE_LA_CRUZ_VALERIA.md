# Evidencia de Contribución C03: Integración Web
**Integrante:** Valeria Hernández De la Cruz  
**Criterio R04 Asignado:** 2. Angular (1.5 Puntos)  
**Módulos Integrados:** M12 (API REST) y M13 (Cliente Web)

## Resumen de la Contribución
En el Corte 3 (C03), consolidé el cliente web en Angular garantizando la separación estricta entre componentes coordinadores y de presentación, el consumo reactivo de la API REST mediante `HttpClient`, la navegación fluida sin recargas y la representación accesible de los 5 estados asíncronos de la interfaz.

### Problema Resuelto
Un error frecuente en el desarrollo frontend es omitir los estados intermedios de comunicación asíncrona, provocando pantallas en blanco o fallos silenciosos ante retrasos o caídas del servidor. Se verificó que la aplicación maneje de forma explícita y accesible cada transición de estado.

### Solución Implementada y Evidencias
- **Navegación (`app.routes.ts`):** Rutas declaradas con títulos accesibles (`/productos`, `/productos/nuevo`, `/productos/:codigo`, `/ventas`) antes de la ruta comodín `**`.
- **Estados Asíncronos Demostrados:**
  - *Idle:* Estado de reposo antes de la emisión de solicitudes.
  - *Loading:* Spinner visual con `role="status"` y `aria-live="polite"`.
  - *Empty:* Aviso amigable cuando la lista de productos retorna un arreglo vacío.
  - *Success:* Renderizado de productos con precio, existencia y botones de acción.
  - *Error:* Alerta clara (`role="alert"`) que captura errores de red o códigos HTTP 500 sin romper la aplicación.
- **Pruebas:** Verificación de las 9 pruebas unitarias en verde con Jasmine/Karma (`TOTAL: 9 SUCCESS`).

### Trazabilidad en GitHub
- **Rama:** `feature/c03-angular-navegacion-estados-ui`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/74 (#74)
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/75 (#75)
- **Revisor Asignado:** Alejandro Lezama Aguilar