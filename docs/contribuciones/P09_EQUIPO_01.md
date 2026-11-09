# UNIVERSIDAD VERACRUZANA
## Tecnologías para la Construcción de Software (TCSW-19234)
# P09_EQUIPO_01
Evidencia de Equipo: Práctica P09 (API de Ventas)

**Módulo Integrado:** M12 (API básica)
**Instrumento de Evaluación:** R01 
**Repositorio Oficial:** https://github.com/maurihm/flujo-colaborativo-ventas
**Rama de Entrega:** `main`
**Integrantes (Equipo 01):**
1. Alejandro Lezama Aguilar
2. Valeria Hernández De la Cruz
3. Mauricio Hernández Martínez
4. Axel Uriel García Loyo

## Índice de Evidencias y Trazabilidad

| Integrante | PR y Rama | Descripción de la Contribución | Estado |
| :--- | :--- | :--- | :--- |
| **Alejandro Lezama** | PR #57 `feature/p09-spring-config-producto-controller` | Configuración Spring Boot 2.7, DTOs de Producto, `ProductoRestController`, manejador global RFC 9457 `ApiExceptionHandler` y clase arrancadora. | VERIFICADO |
| **Valeria Hernández** | PR #59 `feature/p09-contrato-openapi-cors` | Contrato OpenAPI 3.1.0 (`openapi.yaml`), catálogos de ejemplos JSON representativos en `docs/ejemplos/` y configuración de seguridad `CorsConfig`. | VERIFICADO |
| **Mauricio Hernández** | PR #61 `feature/p09-venta-controller-adapter` | Implementación de `VentaRestController`, DTOs de venta (`CrearVentaRequestDto`, `ItemVentaDto`, `VentaResponseDto`) e integración con `VentaFacade`. | VERIFICADO |
| **Axel Uriel García** | PR #63 `feature/p09-pruebas-mockmvc-sonar` | Suite de pruebas unitarias MockMvc (`ProductoRestControllerTest`, `VentaRestControllerTest`), test de arquitectura `RestArchitectureTest`, reporte SonarQube e índice. | VERIFICADO |

### Declaración de Estados de Verificación
- **VERIFICADO:** Integración de API HTTP, endpoints funcionales en Tomcat puerto 8080 con Spring Boot, suite de pruebas en verde y calidad del código asegurada con 0 Code Smells y 0 Vulnerabilidades.
- **NO VERIFICADO:** Ninguno.
- **PENDIENTE:** Ninguno.
