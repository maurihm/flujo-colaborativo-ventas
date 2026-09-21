# ADR 01: Adopción de Arquitectura Hexagonal para el Prototipo de Ventas

## Contexto
El prototipo de ventas solía seguir una secuencia directa. El problema con ese enfoque es que si las reglas del negocio conocen componentes gráficos o de persistencia (como Swing o SQL), cada cambio tecnológico arrastra al resto del sistema, dificultando las pruebas aisladas.

## Decisión
Se decidió implementar Arquitectura Hexagonal (Puertos y Adaptadores) para organizar el sistema separando el núcleo (Dominio y Aplicación) de los actores externos (Adaptadores).
- **Puertos de entrada:** Se definió `RegistrarVentaUseCase` para aislar la intención del actor (usuario/test) sin conocer la interfaz gráfica.
- **Puertos de salida:** Se definió `VentaRepository`, invirtiendo la dependencia para que el dominio exprese su necesidad sin acoplarse a una base de datos específica.
- **Adaptadores:** Se implementó `InMemoryVentaRepository` como una alternativa temporal y sustituible.

## Consecuencias y Costos
- **Positivas:** El núcleo ahora puede probarse de manera 100% independiente mediante pruebas unitarias y ArchUnit, sin levantar infraestructura externa. El dominio está protegido.
- **Negativas/Costos:** Introducir esta arquitectura aumenta la complejidad inicial. Requiere mantener contratos explícitos (interfaces), crear objetos de transferencia de datos y ensamblar dependencias en una raíz de composición. Sin embargo, este costo se justifica por la necesidad futura de agregar persistencia real y clientes Swing.
# Decisiones Arquitectónicas (ADR) - Práctica P05

## Contexto
El prototipo de ventas actual contiene el dominio y sus pruebas unitarias. Para la Práctica P05, se nos ha solicitado transformar el proyecto utilizando la Arquitectura Hexagonal (Puertos y Adaptadores) para aislar las reglas de negocio de cualquier tecnología externa.

## Decisiones Tomadas

### 1. Uso estricto de paquetes `ports/in` y `ports/out`
Se decidió separar los puertos explícitamente en dos subpaquetes:
- **`ports/in` (Entrada):** Aquí residen los Casos de Uso (interfaces). Es lo que nuestro núcleo le ofrece al mundo exterior (ej. `RegistrarProductoUseCase`).
- **`ports/out` (Salida):** Aquí residen los repositorios (interfaces). Es lo que nuestro núcleo necesita del mundo exterior para funcionar (ej. `ProductoRepository`).

Esta separación garantiza una frontera clara en el flujo de dependencias y facilita la escritura de reglas de ArchUnit.

### 2. Organización de Adaptadores por Tecnología (`adapters/out/memory`)
En lugar de crear un paquete genérico de adaptadores, se optó por la estructura `adapters/out/memory`.
- **Por qué `out`:** Porque el repositorio envía información hacia el exterior para ser almacenada.
- **Por qué `memory`:** Porque actualmente la práctica nos exige no usar bases de datos reales. Al encapsular la solución temporal (HashMaps) en un paquete `memory`, preparamos el terreno para el futuro. Si en próximos módulos se requiere una base de datos MySQL, simplemente se creará el paquete `adapters/out/sql` sin necesidad de modificar el código existente en `memory` o en el dominio.

### 3. Validación Continua con ArchUnit
Se integró la librería `archunit-junit5` y se creó la clase `ArchitectureTest.java` para asegurar automatizadamente que el núcleo (`domain` y `application`) nunca importe código de los `adapters` ni dependa de frameworks prohibidos (como `java.sql` o `javax.swing`).

## Consecuencias
- El sistema es ahora 100% agnóstico a la base de datos o interfaz de usuario.
- Los casos de uso pueden probarse aisladamente inyectando las implementaciones en memoria.
- La curva de aprendizaje del equipo se mitigó gracias a la clara división de responsabilidades.
