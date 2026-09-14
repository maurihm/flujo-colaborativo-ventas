# Reporte de Contribución Individual

* **colaborador:** Valeria  
* **Rol / Tarea Asignada:** Lógica de negocio y cálculo de IVA en ventas.
* **Issue Relacionado:** [#2 - Cálculo de IVA en Venta](https://github.com/maurihm/flujo-colaborativo-ventas/issues/2)
* **Rama de Trabajo:** `2-cálculo-de-iva-en-venta`

---

## 1. Descripción de la Contribución
Se implementó el requerimiento correspondiente al cálculo automático del Impuesto al Valor Agregado (IVA del 16%) sobre el subtotal de los productos agregados en una transacción, garantizando la inmutabilidad de los objetos y la precisión numérica en el dominio.

## 2. Archivos Modificados / Creados
* **`Venta.java`:** Se agregaron los métodos `calcularSubtotal()` para obtener la suma limpia de los detalles y `calcularTotal()` para aplicar la tasa del 16% de IVA.
* **`Dinero.java`:** Se extendió el Objeto Valor (Value Object) agregando una sobrecarga al método `multiplicar` para permitir factores decimales de manera segura.
* **`VentaTest.java`:** Se actualizaron y ampliaron las pruebas unitarias con JUnit 5 para validar el comportamiento correcto tanto del subtotal como del total final con impuestos.

## 3. Evidencia de Calidad y Pruebas (TDD)
Se verificó la ejecución exitosa de todas las pruebas unitarias mediante Maven antes de solicitar la revisión por pares:

```text
[INFO] -------------------------------------------------------
[INFO]   T E S T S
[INFO]-------------------------------------------------------
[INFO] Running ventas.domain.DineroTest (5/5 tests passed)
[INFO] Running ventas.domain.ProductoTest (9/5 tests passed)
[INFO] Running ventas.domain.VentaTest (5/5 tests passed)
[INFO] BUILD SUCCESS
