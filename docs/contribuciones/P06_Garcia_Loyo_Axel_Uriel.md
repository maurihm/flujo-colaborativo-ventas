# Evidencia de Participación - Patrones de Diseño (P06)

**Integrante:** GARCIA LOYO AXEL URIEL
**Rol/Patrón asignado:** Facade y Análisis Crítico (Documentación)

## Contexto de la Tarea (Issue / PR)
- **Issue:** Facade: punto de entrada y documentación P06
- **Rama:** `feature/facade`
- **Pull Request:** Feature/Docs: Facade y documentación técnica P06

## Implementación Técnica (Patrón Facade)
### Problema que resuelve
El flujo para crear una venta, validar detalles, guardarla y notificar a los observadores requería múltiples pasos manuales desde el cliente. Además, la implementación del `DetalleVentaFactory` sugerida no era compatible con la firma original del método `agregarDetalle` en la clase `Venta`.

### Solución aplicada (Patrón Facade y Sobrecarga)
1. Se implementó `VentaFacade` para orquestar las fábricas, el repositorio y los servicios en un único método `procesarVentaCompleta()`.
2. Se aplicó **sobrecarga de métodos** en `Venta.java` agregando `agregarDetalle(DetalleVenta detalle)`. Esto permitió integrar la fábrica sin romper las pruebas unitarias existentes del resto del equipo.
3. Se redactó el documento ADR rechazando el patrón Singleton por generar acoplamiento global y romper el aislamiento de las pruebas.

### Archivos creados / modificados
1. `src/main/java/ventas/application/VentaFacade.java` (Creado)
2. `src/test/java/ventas/application/VentaFacadeTest.java` (Creado)
3. `docs/P06-patrones.md` (Creado)
4. `docs/contribuciones/P06_GARCIA_LOYO_AXEL_URIEL.md` (Creado)
5. `src/main/java/ventas/domain/Venta.java` (Modificado - Sobrecarga)

## Pruebas
Se creó la prueba `VentaFacadeTest` utilizando infraestructura en memoria (`InMemoryVentaRepository`). Se verifica que la fachada logre crear la venta con la fábrica, asigne el detalle correctamente mediante la sobrecarga, y que el estado final cambie a `PAGADA` al procesar todo el flujo.

## Costos y Limitaciones de la Decisión
- **Costo:** Rechazar el Singleton requiere inyectar explícitamente las dependencias (Repositorios y Servicios) a través del constructor de la Fachada.
- **Limitación:** El Facade actual asume un flujo lineal de venta directa. Si se requieren flujos alternativos, la clase tendría que expandirse.

## Corrección de Calidad de Código (SonarQube)
Adicional a la implementación del Facade, me encargué de limpiar los 6 *Code Smells* detectados por SonarQube en la rama principal para asegurar un "Quality Gate Passed" perfecto:
- Se reemplazó el uso de `System.out` por `java.util.logging.Logger` en `BitacoraVenta`.
- Se eliminaron variables de asignación inútiles (`totalConDescuento`) y parámetros sin uso (`PoliticaDescuento politica` en el Facade).
- Se retiró el modificador `public` en las clases de prueba (`ObserverTest` y `VentaFacadeTest`) para cumplir con las mejores prácticas de JUnit 5.
- Se actualizó el diagrama de arquitectura para reflejar la desvinculación de `PoliticaDescuento` del `VentaFacade`.