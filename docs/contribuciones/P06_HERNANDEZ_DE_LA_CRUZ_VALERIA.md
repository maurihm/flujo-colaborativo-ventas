# REPORTE DE EVIDENCIA: PRÁCTICA P06 - PATRONES EN VENTAS (STRATEGY)

* **Estudiante:** Valeria Hernández De la Cruz
* **Rama de Trabajo:** `feature/strategy`
* **Issue Relacionado:** Strategy: politicas de descuento intercambiables #20 https://github.com/maurihm/flujo-colaborativo-ventas/issues/20
---

## 1. Objetivo de la Contribución
Implementación del patrón de diseño **Strategy** para hacer que las políticas de descuento sean completamente intercambiables, permitiendo aplicar reglas de negocio flexibles (como sin descuento o descuento de estudiante) sin modificar la lógica principal del dominio ni los casos de uso.

---

## 2. Componentes Desarrollados
* **Interfaz y Estrategias (`ventas.application`):**
  * `PoliticaDescuento.java`: Interfaz que define los métodos contractuales `aplicar(Dinero subtotal)` y `nombre()`.
  * `SinDescuento.java`: Implementación concreta que retorna el subtotal sin modificaciones.
  * `DescuentoEstudiante.java`: Implementación concreta que aplica un descuento del 10% sobre el subtotal.
* **Modificaciones en Aplicación y Dominio:**
  * `ComandoCrearVenta.java`: Actualizado para encapsular la política de descuento seleccionada junto con su respectivo método getter.
  * `CrearVentaService.java`: Modificado para integrar el cálculo de la venta aplicando la estrategia de descuento correspondiente.
* **Pruebas Unitarias:**
  * `StrategyTest.java`: Pruebas unitarias desarrolladas con JUnit 5 para validar el comportamiento correcto de las distintas políticas de descuento.

---

## 3. Evidencias de Ejecución (Build Success)
* Se verificó la correcta compilación del proyecto y la ejecución exitosa de las pruebas unitarias mediante Maven (`mvn test`).

---

## 4. Conclusiones
El uso del patrón Strategy permitió desacoplar los algoritmos de descuento del servicio de aplicación, cumpliendo con los principios de diseño abierto/cerrado (OCP) y facilitando la extensibilidad del sistema de ventas ante futuras reglas comerciales.