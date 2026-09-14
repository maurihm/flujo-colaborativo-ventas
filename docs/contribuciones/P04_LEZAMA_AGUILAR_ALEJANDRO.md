# Bitácora de Contribución Individual - P04

**Integrante:** Alejandro Lezama Aguilar


## 1. Trazabilidad del Cambio
- **Issue asignado:** [#3 - Agregar clase Cliente y asociarla a Venta](https://github.com/maurihm/flujo-colaborativo-ventas/issues/3)
- **Rama de trabajo:** `3-agregar-clase-cliente-y-asociarla-a-venta`
- **Pull Request:** [PR #6](https://github.com/maurihm/flujo-colaborativo-ventas/pull/6)
- **Revisor de pares:** Mauricio Hernández Martínez

**Commits Relevantes:**
* `d0eae5c` - *Cierra #3: agrega clase Cliente y la asocia a Venta*
* `13db786` - *Eliminación de .idea y target del control de versiones*


## 2. Decisión Técnica Justificada
Se creó la clase `Cliente` siguiendo el mismo patrón de validación defensiva que `Producto` (utilizando el constructor con `IllegalArgumentException` para bloquear datos obligatorios inválidos).

Como decisión de diseño de dominio, el teléfono se dejó opcional (puede ser nulo), ya que no representa un dato crítico para la lógica de ventas actual. Adicionalmente, se optó por no modificar el constructor original de `Venta` para **no romper el trabajo en paralelo** del resto del equipo; en su lugar, se implementó el método `asignarCliente()`.


## 3. Pruebas y Comprobación (VERIFICADO)
Se ejecutaron las pruebas unitarias localmente antes de integrar (Merge) para garantizar que no existieran regresiones:
* **Comando ejecutado:** `mvn clean test`
* **Resultado:** `BUILD SUCCESS` (29 tests ejecutados, 0 fallos).
* **Cobertura añadida:** Se incluyeron 8 pruebas nuevas en `ClienteTest` y 2 pruebas adicionales en `VentaTest` para validar el comportamiento esperado y los casos límite.