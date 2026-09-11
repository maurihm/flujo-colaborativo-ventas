# ventas-tcsw

Prototipo progresivo de ventas para la EE **Tecnologías para la Construcción de Software** (UV).
Este incremento corresponde a **P02. Venta en memoria**, y se construye sobre lo entregado en P01.

## Requisitos

- Java 11 (JDK)
- Maven 3.9.x
- Git

Verifica tu entorno antes de continuar:

```bash
java -version
javac -version
mvn -version
```

## Clonar el repositorio

```bash
git clone https://github.com/maurihm/ventas-tcsw.git
cd ventas-tcsw
```

## Compilar y ejecutar las pruebas

```bash
mvn clean test
```

Si todo funciona correctamente, la salida debe terminar con:

```
BUILD SUCCESS
```

## Estructura del proyecto

```
ventas-tcsw/
├── pom.xml
├── README.md
├── .gitignore
├── diagrama_venta.puml
└── src/
    ├── main/java/ventas/domain/
    │   ├── Producto.java
    │   ├── Dinero.java
    │   ├── DetalleVenta.java
    │   └── Venta.java
    └── test/java/ventas/domain/
        ├── ProductoTest.java
        ├── DineroTest.java
        └── VentaTest.java
```

## Modelo de este incremento (P02)

`Venta` compone `DetalleVenta`: una venta controla su propia lista de detalles y nunca la expone
directamente, solo permite modificarla mediante `agregarDetalle(producto, cantidad)`.
`DetalleVenta` referencia a `Producto`, pero no lo compone (un producto existe
independientemente de que aparezca o no en un detalle de venta).

`Dinero` es un objeto valor: representa un monto monetario, es inmutable, y se compara
por su valor (dos instancias con el mismo monto se consideran iguales), no por identidad.
Tanto `Producto` como `DetalleVenta` usan `Dinero` en lugar de un `double` suelto para
representar precios y subtotales.

Diagrama completo (fuente editable en `diagrama_venta.puml`, abrir en
[plantuml.com/plantuml](https://www.plantuml.com/plantuml)).

## Invariantes protegidas en este incremento

1. Una venta no acepta un detalle con cantidad cero o negativa.
2. El precio capturado en un `DetalleVenta` no cambia aunque después cambie el precio
   del `Producto` en el catálogo.
3. La lista de detalles de una `Venta` no puede modificarse directamente desde fuera;
   solo a través de `agregarDetalle()`.
4. `Dinero` nunca representa un monto negativo.

## Decisiones de diseño

| Decisión | Alternativa considerada | Consecuencia |
|---|---|---|
| Crear `Dinero` como objeto valor en lugar de usar `double` directamente para los precios | Mantener `double` suelto en `Producto` y `DetalleVenta` | Se gana una comparación por valor consistente (`equals`/`hashCode`) y operaciones (`sumar`, `multiplicar`) que no permiten construir un monto negativo, a costa de una clase adicional |
| Copiar el precio al detalle (`precioCapturado`) en lugar de consultarlo siempre en `Producto` | Guardar solo una referencia al producto y leer su precio actual al calcular el total | Se duplica un dato, pero se conserva el valor histórico de la venta aunque el catálogo cambie después |
| `Venta` expone `getDetalles()` como lista no modificable (`Collections.unmodifiableList`) | Devolver la lista interna directamente | Se evita que código externo agregue detalles sin pasar por `agregarDetalle()`, protegiendo la invariante de cantidad válida |

## Pruebas incluidas

**DineroTest** (5 pruebas):
creación válida, rechazo de monto negativo, igualdad por valor, suma de montos,
multiplicación por un factor.

**ProductoTest** (7 pruebas):
creación válida, rechazo de precio nulo, rechazo de existencia negativa, descuento válido,
rechazo de descuentos inválidos, conservación de estado tras un rechazo.

**VentaTest** (5 pruebas):
- Agregar un detalle válido y calcular su subtotal.
- Rechazar cantidad cero o negativa al agregar un detalle.
- Calcular el total como suma de varios subtotales.
- Verificar que el precio capturado no cambie si el producto cambia de precio después.
- Verificar que la lista de detalles no se pueda modificar directamente desde fuera.
