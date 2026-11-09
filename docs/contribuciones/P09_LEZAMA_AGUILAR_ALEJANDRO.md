# Evidencia de Contribución P09
**Integrante:** Alejandro Lezama Aguilar  
**Módulo y Componente Asignado:** M12. API básica — Configuración Spring Boot 2.7, DTOs de Producto, ProductoRestController y ApiExceptionHandler (RFC 9457)

## Resumen de la Contribución
Configuré la infraestructura inicial del adaptador web para la API REST del prototipo de ventas utilizando Spring Boot 2.7.18 sobre Java 11. Implementé la clase de arranque `RestApplication`, los objetos de transferencia de datos (`ProductoRequestDto` y `ProductoResponseDto`) con validación declarativa, el controlador `ProductoRestController` desacoplado del dominio y el manejador global de excepciones `ApiExceptionHandler` bajo la especificación RFC 9457 (*Problem Details for HTTP APIs*).

### Problema Resuelto
La arquitectura hexagonal previa exponía los casos de uso exclusivamente a través de la interfaz gráfica Swing. Para cumplir con el módulo M12 y los requerimientos de cliente-servidor HTTP sin acoplar el dominio al framework web ni generar incompatibilidades con Java 11, se requería:
1. Gestionar las dependencias de Spring Boot 2.7 sin colisionar con Hibernate ni con JUnit 5.
2. Exponer operaciones de lectura y creación (`GET /productos`, `GET /productos/{codigo}`, `POST /productos`) con semántica estricta.
3. Desacoplar las representaciones externas de las entidades internas de dominio mediante DTOs.
4. Evitar la exposición de trazas de error o detalles internos del servidor ante fallos o entradas inválidas.

### Solución Implementada
- **`pom.xml`:** Declaración de `spring-boot-dependencies` (2.7.18) en `dependencyManagement` y adición de `spring-boot-starter-web` y `spring-boot-starter-validation`.
- **`RestApplication.java`:** Componente arrancador con inyección de dependencias para los casos de uso (`RegistrarProductoUseCase`, `VentaFacade`) y repositorios.
- **`ProductoRequestDto.java` y `ProductoResponseDto.java`:** Estructuras de datos con validaciones `@NotNull`, `@NotBlank`, `@DecimalMin` y `@Min`.
- **`ProductoRestController.java`:** Adaptador primario que traduce las peticiones HTTP a comandos de aplicación (`ComandoRegistrarProducto`) y retorna respuestas con códigos 200 OK y 201 Created con cabecera `Location`.
- **`ProblemDetailDto.java` y `ApiExceptionHandler.java`:** Manejador `@RestControllerAdvice` que captura fallos de validación (`400 Bad Request`), recursos inexistentes (`404 Not Found`) y excepciones generales (`500`), sanitizando las respuestas sin filtrar trazas.

### Evidencias en GitHub
- **Rama de Trabajo:** `feature/p09-spring-config-producto-controller`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/56
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/57
- **Revisora Asignada:** Valeria Hernández De la Cruz
