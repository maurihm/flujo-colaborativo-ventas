# Evidencia de Contribución P09
**Integrante:** Valeria Hernández De la Cruz  
**Módulo y Componente Asignado:** Contrato OpenAPI 3.1.0, Ejemplos JSON y Configuración de CORS  

## Resumen de la Contribución
Diseñé el contrato formal de la API en `docs/openapi.yaml` bajo el estándar OpenAPI 3.1.0, documentando los recursos `/productos` y `/ventas` con sus respectivas respuestas de éxito y error estandarizadas en RFC 9457. Asimismo, generé el catálogo de ejemplos JSON y configuré las políticas de seguridad CORS en el adaptador web.

### Problema Resuelto
La arquitectura de microservicios y APIs REST requiere un contrato oficial desacoplado para la documentación y futuros clientes de frontend, además de controlar los orígenes seguros permitidos mediante CORS.

### Solución Implementada
- **openapi.yaml:** Especificación OpenAPI 3.1.0 con esquemas de solicitud/respuesta y Problem Details.
- **docs/ejemplos/:** JSONs representativos de peticiones y respuestas exitosas y fallidas (400, 404).
- **CorsConfig.java:** Configuración global de cabeceras y métodos permitidos.

### Evidencias en GitHub
- **Rama:** `feature/p09-contrato-openapi-cors`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/58
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/59
- **Revisor Asignado:** Alejandro Lezama Aguilar