# Evidencia - Actividad P04 (Flujo colaborativo GitHub)

Este documento relaciona las evidencias de nuestro repositorio con los criterios de evaluación de la rúbrica

### 1. Funcionamiento
* **Evidencia:** Las funcionalidades implementadas en nuestros 4 incrementos (Cálculo de IVA, Asociación de Cliente, Categoría de Producto y Estado de Venta) compilan y funcionan bajo el contrato del dominio.
* **Reproducción:** Al ejecutar `mvn clean test` desde la raíz, se obtiene `BUILD SUCCESS`. Esto comprueba que el comportamiento esperado y los casos de error están cubiertos y no existen fallos.

### 2. Solución técnica
**Evidencia:** La estructura de los commits, las ramas y el código es congruente con el diseño de Dominio y Orientación a Objetos. Se aplicó programación defensiva, encapsulamiento e inmutabilidad (ej. `EstadoVenta` Enum, validaciones con `IllegalArgumentException` e `IllegalStateException`).
**Ver:** Los archivos modificados en `src/main/java/ventas/domain/`.

### 3. Pruebas
**Evidencia:** Todos los PRs incluyeron casos de pruebas unitarias (`VentaTest`, `ProductoTest`, `DineroTest`).
**Ver:** Las capturas y logs de ejecución en nuestros documentos individuales y en los comentarios de los PRs. Hay casos positivos y negativos documentados.

### 4. Calidad y Sonar 
**Evidencia:** Las Pull Requests sirvieron como herramienta principal de revisión de calidad por pares. Cada PR fue inspeccionado por un integrante distinto al autor.
**Ver:** Los comentarios de revisión, sugerencias y resolución de bloqueantes en GitHub (PR #4, #5, #6, #8).

### 5. Git y evidencia 
**Evidencia:** Trazabilidad completa con Issues, Ramas cortas, PRs, y Merge. Cada integrante aportó su bitácora individual que detalla su participación.
**Archivos individuales:**
- `docs/contribuciones/P04_LEZAMA_AGUILAR_ALEJANDRO.md`
- `docs/contribuciones/P04_HERNANDEZ_MARTINEZ_MAURICIO.md`
- `docs/contribuciones/P04_GARCIA_LOYO_AXEL_URIEL.md`
- `docs/contribuciones/P04_VALERIA_HERNANDEZ_DE_LA_CRUZ.md` 


**Guía Breve de Reproducción:**
1. Asegurarse de tener Java 11 y Maven instalados.
2. Posicionarse en la raíz del proyecto.
3. Ejecutar `mvn clean test`.
4. Revisar el historial con `git log --graph --oneline` para ver el flujo de ramas y merges de todos los integrantes.
