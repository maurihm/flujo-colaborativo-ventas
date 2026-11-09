# Evidencia de Contribución P09
**Integrante:** Axel Uriel García Loyo
**Módulo y Componente Asignado:** M12 - Suite de Pruebas MockMvc, Reglas ArchUnit e Interpretación SonarQube.

## Resumen de la Contribución
Desarrollé la suite de pruebas unitarias y de integración de controladores mediante `MockMvc` para asegurar el cumplimiento del contrato HTTP, verificando tanto respuestas de éxito como excepciones de dominio y validación. Complementé el entorno de calidad con reglas arquitectónicas en `ArchUnit` y documenté el índice consolidado del equipo.

### Problema Resuelto
El equipo había construido la infraestructura web, pero era necesario verificar automatizadamente que los códigos HTTP (200, 201, 400, 404) y los cuerpos JSON estructurados según RFC 9457 se retornaran correctamente ante peticiones exitosas y fallidas, evitando usar Postman de forma manual cada vez.

### Solución Implementada
- **`ProductoRestControllerTest.java`:** Validación de rutas de catálogo, creación (201 Created) y fallos de Bean Validation (400 Bad Request) probando `jsonPath`.
- **`VentaRestControllerTest.java`:** Comprobación del flujo de delegación en `VentaFacade` para confirmar la venta, o lanzar excepciones como `NoSuchElementException` (404).
- **`RestArchitectureTest.java`:** Se incluyeron dos reglas nuevas de ArchUnit que impiden que el núcleo dependa de Spring Web o `adapters.in.rest`.
- **`P09_EQUIPO_01.md`:** Creación del índice de trazabilidad para todos los integrantes.

### Evidencias en GitHub
- **Rama:** `feature/p09-pruebas-mockmvc-sonar`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/62
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/63
- **Revisor Asignado:** Mauricio Hernández Martínez
