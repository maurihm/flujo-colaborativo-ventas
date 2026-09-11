**Nombre:** GARCIA LOYO AXEL URIEL
**Rol:** Autor de implementación técnica y Revisor de pares

**Issue Vinculado:** #4 - Control de Estados (EstadoVenta)

**Descripción de la Tarea**
Agregar un Enum `EstadoVenta` (NUEVA, PAGADA, CANCELADA) para controlar el ciclo de vida de la transacción comercial.

**Criterios de Aceptación Cumplidos**

* Una venta recién creada inicia automáticamente en estado `NUEVA`.


* El sistema bloquea modificaciones y protege los datos, permitiendo agregar objetos `DetalleVenta` únicamente si la venta sigue abierta.
* Se lanzan excepciones específicas (`IllegalStateException`) si se intenta alterar una venta cobrada o cancelada.

**¿Qué implementé?**

1. **Diseño de Dominio:** Creación del enumerador para mapear los tres estados lógicos del negocio.
2. **Programación Defensiva:** Inyección de un "candado de seguridad" en el método principal de modificación de la venta para proteger el encapsulamiento y el invariante de la clase.


3. **Pruebas Unitarias:** Implementación de dos casos de prueba aislados utilizando `JUnit` para validar la transición de estados y el rechazo de productos inválidos. Se depuraron errores de tipos estáticos (`long` vs `String`) para alinear las pruebas al contrato original del proyecto.



**Archivos Modificados y Creados**

* *Creado:* `src/main/java/ventas/domain/EstadoVenta.java` (Estructura base del Enum).
* *Modificado:* `src/main/java/ventas/domain/Venta.java` (Adición del estado inicial en el constructor y validación defensiva dentro de `agregarDetalle`).
* *Modificado:* `src/test/java/ventas/domain/VentaTest.java` (Adición de los métodos `ventaNuevaPermiteAgregarDetalles()` y `agregarDetalleAVentaPagadaLanzaExcepcion()`).

**Evidencia de Verificación**
Ejecución local exitosa mediante `mvn clean test` (BUILD SUCCESS, 0 Failures), confirmando la integridad del incremento sin romper funcionalidades existentes.

