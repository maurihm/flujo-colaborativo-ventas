# Bitácora de Contribución Individual - P05

- **Integrante:** Mauricio Hernández Martínez
- **Rol en la actividad:** Autor del caso de uso "Registrar Producto"
- **Módulo:** M05. Arquitectura Hexagonal
- **Experiencia Educativa:** Tecnologías para la Construcción de Software

## 1. Trazabilidad del Cambio
- **Caso de uso:** `Registrar Producto`
- **Rama de trabajo:** `feature/registrar-producto`
- **Revisor asignado:** Axel Uriel García Loyo

## 2. Alcance y Decisión Técnica
Se implementó el caso de uso `Registrar Producto` bajo los principios de arquitectura hexagonal y el patrón comando.
- Se definió el objeto `ComandoRegistrarProducto`.
- Se creó la interfaz de entrada `RegistrarProductoUseCase`.
- Se creó la interfaz de salida `ProductoRepository`.
- Se implementó el servicio de aplicación `RegistrarProductoService`.
- Se creó el adaptador en memoria `InMemoryProductoRepository`.
- Se actualizó la clase `ArchitectureTest.java` para excluir explícitamente los paquetes de pruebas del análisis de dependencias mediante `ImportOption.DoNotIncludeTests.class`, lo cual permitió utilizar adaptadores en memoria en las clases de prueba (incluyendo este caso de uso y el de CrearVenta) sin disparar falsos positivos de ArchUnit.

## 3. Pruebas y Comprobación
Se ejecutaron pruebas automatizadas con JUnit 5:
- **Caso positivo:** Registro de un producto correctamente validado y su almacenamiento en el repositorio en memoria.
- **Caso límite / error:** Intento de registro con nombre nulo para verificar que se lance un `IllegalArgumentException`.
- **Resultado Maven:** `BUILD SUCCESS` (`mvn test`), validando todas las pruebas funcionales y las reglas de ArchUnit.
- **Estado de comprobación:** VERIFICADO.
