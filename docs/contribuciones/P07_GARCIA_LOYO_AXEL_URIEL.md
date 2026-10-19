# Evidencia de Contribución P07
**Integrante:** Axel Uriel García Loyo
**Módulo y Componente Asignado:** M09

## Resumen de la Contribución
Controlador MVC, SwingWorker y MainFrame
Implementé VentaSwingController con SwingWorker, la ventana principal MainFrame y el ejecutable SwingApplication.

### Solución Implementada
- Conexión MVC entre paneles y casos de uso del dominio.
- SwingWorker asíncrono para mantener libre el EDT y no congelar la pantalla.
- Estados observables (LISTA, EJECUTANDO con botones deshabilitados, ÉXITO / ERROR).
- Inicialización en el EDT mediante `SwingUtilities.invokeLater`.

### Evidencias en GitHub
- **Rama:** `feature/controlador-swingworker-app`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/34
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/35
