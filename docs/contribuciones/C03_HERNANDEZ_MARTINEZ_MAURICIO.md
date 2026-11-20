# Evidencia de Contribución C03: Integración Web
**Integrante:** Mauricio Hernández Martínez  
**Criterios R04 Asignados:** 3. Reutilización del núcleo (1.0 pt) y 4. Errores y validación (0.5 pts)  
**Módulos Integrados:** M12 (API REST Spring Boot) y M13 (Cliente Web Angular)

## Resumen de la Contribución
En el Corte 3 (C03), verifiqué y documenté que la integración web (API REST en Spring Boot y cliente Angular) reutiliza íntegramente el núcleo de dominio y los casos de uso existentes sin duplicación improcedente ni acoplamientos hacia infraestructura, y comprobé el manejo controlado de validaciones y errores conforme al estándar RFC 9457 (`ProblemDetail`) tanto en el servidor como en los formularios reactivos del cliente web.

### Problema Resuelto
En aplicaciones web acopladas es frecuente que los controladores HTTP reimplementen cálculos de negocio, consulten directamente la base de datos o expongan trazas internas (`stack traces`) ante entradas erróneas, y que el frontend envíe solicitudes inválidas sin validación previa ni retroalimentación accesible. Se verificó que toda la lógica crítica permanezca encapsulada en el dominio puro y que las entradas inválidas o recursos inexistentes respondan de forma controlada y predecible.

### Solución Implementada y Evidencias
- **Reutilización del Núcleo (Arquitectura Hexagonal):**
  - `ProductoRestController`: Delega el alta en `RegistrarProductoUseCase` (`RegistrarProductoService`) traduciendo `ProductoRequestDto` a `ComandoRegistrarProducto` y `Dinero`, sin contener reglas de negocio ni consultas SQL.
  - `VentaRestController`: Delega el registro, persistencia y confirmación de ventas en `VentaFacade` (`ConfirmarVentaService`, `VentaFactory` y observadores), reutilizando las mismas reglas que la aplicación de escritorio Swing de C02.
  - `Dinero`, `Producto`, `Venta` y `DetalleVenta`: Invariantes de dominio, aritmética inmutable y cálculo de subtotal/IVA ejecutados únicamente en el núcleo Java puro.
  - `RestArchitectureTest` y `ArchitectureTest`: 5 pruebas de ArchUnit en verde (2 reglas REST + 3 reglas hexagonales generales) que certifican que `domain` y `application` no dependen de `adapters.in.rest`, `adapters` ni frameworks (`java.sql`, `javax.swing`, `org.springframework`).
- **Tratamiento de Errores y Validación Controlada (RFC 9457):**
  - `ApiExceptionHandler` (`@RestControllerAdvice`) y `ProblemDetailDto`: Captura centralizada de `MethodArgumentNotValidException` (`400 Bad Request` con `invalidParams`), `NoSuchElementException` (`404 Not Found`), `IllegalArgumentException` (`400 Bad Request`) y `Exception` (`500 Internal Server Error`) con estructura RFC 9457 (`type`, `title`, `status`, `detail`, `instance`, `timestamp`, `invalidParams`).
  - **Comprobación negativa en vivo — Recurso inexistente (`404`):** `GET /productos/99999` retornó `HTTP/1.1 404` con `type: "https://api.ventas.tcsw/errores/recurso-inexistente"`, `title: "Recurso no encontrado"` y `detail: "No existe el producto con codigo: 99999"`.
  - **Comprobación negativa en vivo — Entrada inválida (`400`):** `POST /productos` omitiendo el campo obligatorio `existencia` retornó `HTTP/1.1 400` con `type: "https://api.ventas.tcsw/errores/validacion"`, `title: "Entrada invalida"` e `invalidParams: ["La existencia es obligatoria"]`.
  - `ProductoFormComponent`: Formulario reactivo (`ReactiveFormsModule`) en `/productos/nuevo` con validadores síncronos (`required`, `min(1)`, `minLength(2)`, `maxLength(100)`, `min(0.01)`, `min(0)`), bloqueo de envío ante formulario inválido (`onSubmit()` marca controles tocados sin invocar al servicio), mensajes accesibles asociados mediante `aria-describedby` y alertas `role="alert"` para errores `ProblemDetail` del backend.
  - **Pruebas automatizadas verificadas:** `VentaRestControllerTest` (3/3 pruebas MockMvc en verde: `201 Created`, `404 Not Found` y `400 Bad Request`), `ProductoRestControllerTest` (5/5 en verde), `RestArchitectureTest` + `ArchitectureTest` (5/5 en verde), suite completa backend `mvn test` (78/78 en verde, 0 fallos, 0 errores) y compilación de producción Angular `ng build --configuration production` sin errores TypeScript.

### Declaración de Estados de Verificación
- **VERIFICADO:** Delegación de controladores REST en casos de uso puros; pruebas ArchUnit (`RestArchitectureTest` y `ArchitectureTest`, 5/5); pruebas MockMvc (`VentaRestControllerTest` 3/3 y `ProductoRestControllerTest` 5/5); suite backend completa (`mvn test`, 78/78 con PostgreSQL); respuestas RFC 9457 en vivo (`404 Not Found` y `400 Bad Request` con `invalidParams`); validación reactiva en `ProductoFormComponent` y build de producción Angular (`ng build --configuration production`).
- **NO_VERIFICADO:** Ejecución de `npm test -- --watch=false --browsers=ChromeHeadless` en este entorno local (sin binario de Chrome/Chromium instalado; corresponde a la verificación final de la suite en el Paso 4 por Axel Uriel García Loyo).
- **PENDIENTE:** Revisión y aprobación de este Pull Request por Axel Uriel García Loyo, ejecución del cierre de calidad (Paso 4) y publicación de la etiqueta oficial `v0.3-web`.

### Trazabilidad en GitHub
- **Rama:** `feature/c03-reutilizacion-nucleo-rfc9457-validacion`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/77 (#77)
- **Pull Request (PR):** Pendiente de asignación al abrir PR
- **Revisor Asignado:** Axel Uriel García Loyo (@kaos-gl)
