**Nombre:** GARCIA LOYO AXEL URIEL
**Issue y PR:** Issue #4 y Pull Request #5 (EstadoVenta y validación).
**Identificador de Commit Final:** 7c95105e961663f70871d25afcf1c6026aba316b

**Implementación:** Agregué el Enum EstadoVenta e implementé programación defensiva en Venta.java para bloquear modificaciones si la orden no tiene el estado NUEVA. Esto cumple con el requisito de separar qué se espera de cómo se implementa. 

**Pruebas Unitarias:** Se crearon métodos en VentaTest.java verificando el estado inicial y validando que intentar agregar un producto a una venta pagada lance un IllegalStateException. Se depuraron errores de tipos estáticos (long vs String) para alinear las pruebas al contrato original del proyecto. El resultado local fue VERIFICADO mediante mvn clean test. 

**Revisión de Pares:** Actué como revisor en el PR #6 de @Lez267. Identifiqué un riesgo en el ciclo de vida de la transacción al asignar clientes y dejé comentarios bloqueantes y sugerencias sustentadas en el código. 

**Archivos Modificados y Creados:**

·	Creado: src/main/java/ventas/domain/EstadoVenta.java (Estructura base del Enum).
·	
·	Modificado: src/main/java/ventas/domain/Venta.java (Adición del estado inicial en el constructor y validación defensiva dentro de agregarDetalle).
·	
·	Modificado: src/test/java/ventas/domain/VentaTest.java (Adición de los métodos ventaNuevaPermiteAgregarDetalles() y agregarDetalleAVentaPagadaLanzaExcepcion()).
·	
**Retrospectiva:** La revisión temprana y el uso de PRs pequeños detectó fallos cerca de su origen, evitando regresiones en el código base del equipo. 
