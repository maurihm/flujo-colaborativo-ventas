# Evidencia de Contribución P10
**Integrante:** Axel Uriel García Loyo
**Módulo y Componente Asignado:** M13. Angular básico — Pruebas Unitarias, Build de Producción, Calidad de Código y Consolidación de Equipo.

## Resumen de la Contribución
Configuré y desarrollé la suite de pruebas unitarias para el frontend en Angular 20, asegurando la robustez de los servicios y componentes clave. Utilicé `TestBed` y `HttpClientTestingModule` para probar el `ProductosService`, simulando peticiones HTTP y verificando el manejo de errores. Además, implementé pruebas para el componente standalone `ProductoFormComponent`, validando su reactividad y reglas de negocio. Posteriormente, garanticé que el empaquetado final (`ng build --configuration production`) estuviera libre de errores de TypeScript, y ejecuté un análisis de calidad con SonarQube para el proyecto completo, mitigando *Code Smells* y asegurando el cumplimiento del *Quality Gate*. Finalmente, consolidé la trazabilidad del equipo integrando el índice de evidencias.

## Problema Resuelto
Una vez desarrollada la interfaz web, era imperativo garantizar su estabilidad y calidad técnica antes del despliegue:
1. El servicio `ProductosService` requería validación automatizada para asegurar que las peticiones a la API y el manejo de excepciones HTTP funcionaran correctamente sin depender del backend real.
2. El formulario reactivo de productos necesitaba pruebas que comprobaran el bloqueo de envíos con datos inválidos (como precios negativos o campos vacíos).
3. Los componentes actualizados a la arquitectura Standalone requerían un ajuste en la configuración de `TestBed` (moviéndolos de `declarations` a `imports`).
4. Era necesario verificar que el código TypeScript compilara estrictamente para producción mediante los bundles de Angular, sin errores que rompieran el despliegue.
5. El proyecto completo (backend y frontend) presentaba *Code Smells* residuales (imports sin usar, bloques vacíos) que debían solucionarse para cumplir con las reglas estrictas de SonarQube y obtener un reporte limpio.

## Solución Implementada
- **`productos.service.spec.ts`:** Implementación de pruebas con `HttpTestingController` para simular la creación exitosa de un producto y verificar que el servicio maneja adecuadamente los errores HTTP (como códigos 400).
- **`producto-form.component.spec.ts`:** Ajuste del `TestBed` para soportar `ProductoFormComponent` como Standalone Component en el arreglo de `imports`. Se escribieron pruebas que validan los estados de los campos (ej. precio > 0) y el comportamiento del botón de envío (`onSubmit`).
- **Build de Producción:** Ejecución del comando `ng build --configuration production`, validando la generación exitosa de los bundles optimizados en la carpeta `dist/`.
- **SonarQube:** Refactorización de código en `ProductoRestController.java` y `SwingApplication.java` para eliminar *Code Smells*, logrando 0 bugs y un *Quality Gate* en estado *Passed*.
- **Trazabilidad:** Integración de la documentación del equipo en `P10_EQUIPO_01.md`, vinculando correctamente cada Pull Request con su responsable.

## Evidencias en GitHub
- **Rama de Trabajo:** `feature/p10-pruebas-unitarias-sonar`
- **Issue:** #71
- **Pull Request (PR):** #70
- **Revisor Asignado:** Mauricio Hernández Martínez
