# Documento de Decisiones de Diseño (ADR) - Práctica P06

## Contexto
Para esta iteración, el objetivo fue mejorar la mantenibilidad y flexibilidad del núcleo hexagonal incorporando patrones de diseño que resolvieran problemas específicos de creación, variabilidad y notificación.

## Patrones Implementados por el Equipo

### 1. Factory (Creación Segura)
- **Problema:** La creación de ventas y detalles se hacía directamente instanciando objetos, esparciendo la validación.
- **Solución:** Se crearon `VentaFactory` y `DetalleVentaFactory` para centralizar la instanciación y garantizar que los objetos nazcan con un estado válido (invariantes protegidas).

### 2. Strategy (Políticas Flexibles)
- **Problema:** La lógica para calcular descuentos estaba acoplada en los casos de uso, dificultando agregar nuevas promociones.
- **Solución:** Se extrajo el cálculo de precios a la interfaz `PoliticaDescuento` (con implementaciones como `SinDescuento` y `DescuentoEstudiante`), permitiendo intercambiar comportamientos sin alterar el dominio.

### 3. Observer (Desacoplamiento de Eventos)
- **Problema:** Confirmar una venta forzaba al servicio a conocer reglas de inventario y bitácora, violando el principio Abierto/Cerrado.
- **Solución:** Se implementó `VentaObserver`. Ahora `ConfirmarVentaService` solo emite el evento `VentaConfirmada`, y los observadores (`ActualizadorInventario`, `BitacoraVenta`) reaccionan de manera independiente.

### 4. Facade (Orquestación Simple)
- **Problema:** Unir todos estos patrones manualmente desde el cliente obligaba a conocer demasiadas clases del sistema y el orden exacto de ejecución.
- **Solución:** Se creó `VentaFacade` como un punto de entrada único que coordina las fábricas, el repositorio y los servicios, ocultando la complejidad del subsistema para el cliente exterior.

## Análisis Crítico: Rechazo del Patrón Singleton
Durante el diseño arquitectónico, se evaluó la posibilidad de utilizar el patrón Singleton para centralizar el acceso a las fábricas o repositorios. 

**Se decidió NO utilizar Singleton.**
En este proyecto se descartó su uso porque oculta las dependencias globales e impide que las pruebas unitarias se ejecuten en aislamiento. Al mantener un estado global, la ejecución de una prueba afectaría los resultados de las demás, rompiendo la confiabilidad de nuestra validación automatizada.