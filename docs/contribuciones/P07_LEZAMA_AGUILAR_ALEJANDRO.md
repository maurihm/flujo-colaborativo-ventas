# Evidencia de Contribución P07
**Integrante:** Alejandro Lezama Aguilar  
**Módulo y Componente Asignado:** M08 - Catálogo de Productos y Modelo de Tabla (Swing)

## Resumen de la Contribución
Implementé el modelo de tabla desacoplado `ProductoTableModel` y la vista de catálogo con `JTable` dentro del paquete `ventas.adapters.in.swing`.

### Problema Resuelto
En arquitecturas limpias y Swing, enlazar componentes gráficos directamente con el dominio rompe la regla de dependencias. Además, `JTable` requiere un modelo separable que gestione columnas, tipos de datos y eventos de cambio de forma independiente a la vista.

### Solución Implementada
- **`ProductoTableModel`:** Hereda de `AbstractTableModel` para presentar datos de productos en cinco columnas (Código, Nombre, Precio, Stock y Categoría). Maneja eventos de tabla (`fireTableRowsInserted`, `fireTableDataChanged`) y métodos seguros de acceso (`getProductoAt`, `setProductos`).
- **`CatalogoPanel`:** Encapsula la `JTable` dentro de un `JScrollPane`, aplicando `BorderLayout` y asignando nombres accesibles para soporte de accesibilidad.
- **`ProductoTableModelTest`:** Cuatro pruebas unitarias automatizadas con JUnit 5 validando estado inicial, inserción, lectura de celdas y manejo de índices fuera de rango.

### Evidencias en GitHub
- **Rama:** `feature/catalogo-tablemodel`
- **Issue:** [PEGAR LINK DEL ISSUE AQUÍ]
- **Pull Request (PR):** [PEGAR LINK DEL PR AQUÍ]
- **Commit:** [PEGAR LINK DEL COMMIT AQUÍ]
