# Evidencia de Contribución - Axel Uriel García Loyo

## Enlaces de Trazabilidad Git
* **Issue Asignado:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/10
* **Pull Request (PR):**https://github.com/maurihm/flujo-colaborativo-ventas/pull/16

## Resumen de Aportaciones (Issue #10)
Durante la Práctica 05, mi rol consistió en desarrollar el caso de uso final y liderar la integración y documentación de la Arquitectura Hexagonal. Mis aportaciones específicas fueron:

**1. Desarrollo del Caso de Uso (Confirmar Venta)**
* Implementé el puerto de entrada `ConfirmarVentaUseCase`.
* Desarrollé la clase `ConfirmarVentaService` respetando el aislamiento del dominio, orquestando el cambio de estado a `PAGADA`.
* Escribí las pruebas unitarias automatizadas en `ConfirmarVentaServiceTest` validando el correcto funcionamiento mediante el adaptador en memoria.

**2. Actualización de Adaptadores y Mantenimiento**
* Extendí el contrato del puerto de salida `VentaRepository` agregando el método `buscarPorId`.
* Implementé la lógica de búsqueda en el adaptador `InMemoryVentaRepository`.
* Corregí la configuración del plugin de ArchUnit (`ArchitectureTest.java`) para que ignorara las clases de prueba, resolviendo las fallas en el pipeline de validación del equipo.

**3. Documentación Arquitectónica (ADR)**
* Modelé y actualicé el diagrama de dependencias en `diagrama_venta.puml` demostrando la Inversión de Dependencias (adaptadores apuntando al núcleo).
* Redacté el documento de decisiones arquitectónicas (`docs/P05-arquitectura.md`), justificando la separación en puertos y el uso de persistencia en memoria para evitar el acoplamiento temprano.
* Actualicé el `README.md` principal con las instrucciones finales de compilación (`mvn clean test`).
