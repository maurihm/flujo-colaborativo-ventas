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