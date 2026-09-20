# Contribución - Alejandro Lezama Aguilar

**Rol en la Actividad P05:** Configuración Base y Arquitectura

## Actividades Realizadas
1. **Configuración del proyecto (pom.xml):** Agregué la dependencia de `archunit-junit5` para habilitar las pruebas de arquitectura requeridas por la rúbrica.
2. **Estructura Hexagonal:** Creé la estructura de paquetes lógica (`domain`, `application`, `ports/in`, `ports/out`, `adapters/out/memory`) para aislar el núcleo.
3. **Pruebas Estructurales:** Programé la clase `ArchitectureTest.java` con las tres reglas principales (el dominio no debe depender de adaptadores, la aplicación no debe depender de adaptadores y el dominio no debe usar frameworks externos).

## Evidencias Git
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/11
- **Commits:** https://github.com/maurihm/flujo-colaborativo-ventas/pull/12/commits
- **Pull Request:** https://github.com/maurihm/flujo-colaborativo-ventas/pull/12

## Pruebas de Funcionamiento
Las pruebas de ArchUnit y las pruebas del dominio previas ejecutan correctamente y el proyecto marca `BUILD SUCCESS`.
