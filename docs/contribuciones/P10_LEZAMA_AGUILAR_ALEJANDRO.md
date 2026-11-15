# Evidencia de Contribución P10
**Integrante:** Alejandro Lezama Aguilar  
**Módulo y Componente Asignado:** M13. Angular básico — Estructura del Cliente Angular, Proxy de Desarrollo, CORS en Backend, Modelos TypeScript y ProductosService

## Resumen de la Contribución
Configuré la infraestructura base del cliente web en Angular 20 para el prototipo de punto de venta, conectándolo con la API REST Spring Boot desarrollada en el módulo M12. Implementé la configuración del proxy de desarrollo para redirigir las llamadas `/api` hacia `http://localhost:8080`, actualicé el origen permitido `http://localhost:4200` en la configuración CORS del backend, definí los modelos de datos en TypeScript derivados estrictamente del contrato OpenAPI (`Producto`, `NuevoProducto`, `ProblemDetail`) y construí el servicio `ProductosService` encapsulando `HttpClient` y el manejo centralizado de errores con semántica RFC 9457. Asimismo, establecí el enrutamiento base con componentes autocontenidos (Standalone Components).

### Problema Resuelto
El prototipo de ventas contaba con una API REST expuesta en el puerto 8080, pero carecía de una interfaz web cliente moderna para interactuar con los recursos:
1. Durante el desarrollo local, las peticiones directas desde `http://localhost:4200` a `http://localhost:8080` provocaban bloqueos de CORS en el navegador.
2. Era indispensable tipar de manera estricta los datos en el frontend asegurando congruencia con el esquema OpenAPI sin arrastrar dependencias del dominio Java ni anotaciones de Hibernate.
3. Se requería un servicio Angular desacoplado que realizara peticiones HTTP GET y POST transformando los errores del servidor en objetos estructurados con base en RFC 9457.
4. Se necesitaba una arquitectura limpia basada en Angular 20 con componentes Standalone, inyección de dependencias funcional (`provideHttpClient()`, `provideRouter()`) y navegación declarativa accesible.

### Solución Implementada
- **`proxy.conf.json` y `angular.json`:** Configuración de proxy inverso que redirige todas las llamadas al prefijo `/api` hacia el backend en `http://localhost:8080` sin requerir modificar URLs absolutas en el código.
- **`CorsConfig.java`:** Inclusión de `http://localhost:4200` en la lista blanca de orígenes permitidos de Spring Boot para admitir solicitudes cruzadas directas.
- **`producto.model.ts` y `problem-detail.model.ts`:** Interfaces TypeScript que tipan los productos y los detalles de error RFC 9457, garantizando congruencia con el contrato de la API.
- **`productos.service.ts`:** Servicio Angular inyectable en raíz (`providedIn: 'root'`) que implementa `listar()`, `buscarPorCodigo()` y `crear()` retornando Observables tipados, con tubería `.pipe(catchError())` que mapea respuestas 400, 404, 409 y 500 a mensajes amigables para el usuario.
- **`app.routes.ts`, `app.config.ts`, `app.ts` y `app.html`:** Configuración de rutas principales (`/productos`, `/productos/nuevo`, `/productos/:codigo`, `/ventas`), barra de navegación con enlaces accesibles y contenedor dinámico `<router-outlet>`.
- **`app.spec.ts`:** Prueba unitaria inicial de la aplicación configurando el entorno de pruebas con `provideRouter([])`.

### Evidencias en GitHub
- **Rama de Trabajo:** `feature/p10-angular-setup-productos-service`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/64
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/65
- **Revisora Asignada:** Valeria Hernández De la Cruz
