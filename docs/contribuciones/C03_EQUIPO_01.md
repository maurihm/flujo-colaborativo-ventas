# UNIVERSIDAD VERACRUZANA
## Tecnologías para la Construcción de Software (TCSW-19234)
# C03_EQUIPO_01
Evidencia de Equipo: Actividad C03 (Corte 3: Integración Web)

**Módulos Integrados:** M12 (API REST Spring Boot) + M13 (Cliente Web Angular)
**Instrumento de Evaluación:** R04. Integración web (5.00 puntos)
**Repositorio Oficial:** https://github.com/maurihm/flujo-colaborativo-ventas
**Rama Oficial de Entrega:** `main`
**Etiqueta Oficial del Corte:** `v0.3-web`
**Integrantes (Equipo 01):**
1. Alejandro Lezama Aguilar
2. Valeria Hernández De la Cruz
3. Mauricio Hernández Martínez
4. Axel Uriel García Loyo

## 1. Índice de Evidencias por Criterio de la Rúbrica R04

| Criterio R04 | Puntaje | Estado | Evidencias en el Repositorio | Descripción y Justificación |
| :--- | :--- | :--- | :--- | :--- |
| **1. API** | **1.5 pts** | VERIFICADO | `docs/openapi.yaml`<br>`ProductoRestController.java`<br>`RestApplication.java` | Contrato OpenAPI 3.1.0, endpoints REST funcionales en Spring Boot (puerto 8080) con persistencia en PostgreSQL 16 y cumplimiento RFC 9110 / RFC 8259. |
| **2. Angular** | **1.5 pts** | VERIFICADO | `frontend-angular/src/app/**`<br>`productos.service.ts`<br>`app.routes.ts` | Consumo asíncrono vía `HttpClient`, navegación accesible sin recargas y demostración explícita de los 5 estados de interfaz (Idle, Loading, Empty, Success, Error). |
| **3. Reutilización del núcleo** | **1.0 pt** | VERIFICADO | `Producto.java`<br>`Venta.java`<br>`Dinero.java`<br>`VentaFacade.java`<br>`RestArchitectureTest.java` | Cero duplicación de lógica de negocio: controladores REST delegan en casos de uso puros y entidades de dominio, certificado con ArchUnit. |
| **4. Errores y validación** | **0.5 pts** | VERIFICADO | `ApiExceptionHandler.java`<br>`ProblemDetailDto.java`<br>`producto-form.component.ts` | Validación reactiva en Angular bloqueando envíos inválidos con mensajes accesibles y respuestas RFC 9457 `ProblemDetail` (400, 404, 500) en backend. |
| **5. Calidad** | **0.5 pts** | VERIFICADO | `src/test/java/**` (78 tests)<br>`frontend-angular/src/**/*.spec.ts` (9 tests)<br>`C03_EQUIPO_01.md` | 87 pruebas automatizadas en verde, build de producción sin errores TypeScript, SonarQube PASSED (0 issues), flujo Git secuencial y tag `v0.3-web`. |

### Declaración de Estados de Verificación
**VERIFICADO:** Endpoints REST, contrato OpenAPI, cliente Angular, 5 estados asíncronos, arquitectura hexagonal sin duplicación, manejo de errores RFC 9457, 87 pruebas automatizadas, build de producción y análisis SonarQube con 0 issues.
**NO_VERIFICADO:** Ninguno.
**PENDIENTE:** Ninguno.
