# Bitácora de Contribución Individual - P04

- **Integrante:** Mauricio Hernández Martínez
- **Rol en la actividad:** Autor del incremento (Issue #2)
- **Módulo:** M04. Colaboración profesional
- **Experiencia Educativa:** Tecnologías para la Construcción de Software


## 1. Trazabilidad del Cambio
- **Issue asignado:** `#2` - `feat: Categorización de Productos (Categoría)`
- **Rama de trabajo:** `feature/issue-2-categoria-producto`
- **Commits asociados:** (Colocar aquí el hash corto tras hacer commit, ej. `git rev-parse --short HEAD`)
- **Pull Request:** PR `#X` (vincular URL del PR en GitHub)
- **Revisor asignado:** (Nombre del compañero que te revisará el PR)



## 2. Alcance y Decisión Técnica
Se implementó el tipo enumerado `Categoria` en el paquete de dominio `ventas.domain` y se vinculó a la entidad `Producto`. Para preservar la integridad del dominio, se estableció como invariante que ningún producto puede crearse sin una categoría válida (`categoria != null`), rechazando intentos nulos con `IllegalArgumentException`. Para evitar romper el resto del sistema, se conservó un constructor sobrecargado con categoría predeterminada `GENERAL`.


## 3. Pruebas y Comprobación
Se ejecutaron pruebas automatizadas con JUnit 5:
- **Caso positivo:** Creación exitosa de un producto asignándole explícitamente `Categoria.ELECTRONICA` y verificación de `getCategoria()`.
- **Caso límite / error:** Intento de instanciación pasando `null` como categoría, comprobando que se dispare `IllegalArgumentException`.
- **Resultado Maven:** `BUILD SUCCESS` (`mvn test`).
- **Estado de comprobación:** VERIFICADO.


## 4. Revisión por Pares (Code Review)
- **Observaciones recibidas:** (Aquí pegarás el comentario `Sugerencia:` o `Bloqueante:` que tu compañero te deje en el PR).

## Contribución como revisor — PR #8

- Revisé el PR #8 de valux0 ("cálculo de IVA en venta").
- Detecté que incluía la carpeta target/ (artefactos de compilación:
  .class, .jar, jacoco.exec) versionada por error.
- Dejé un comentario clasificado como Bloqueante, explicando ubicación,
  impacto (historial inflado, riesgo de conflictos binarios) y la
  acción concreta para resolverlo (git rm -r --cached target/ + .gitignore).
- Reconocí explícitamente los aspectos correctos del cambio (lógica del
  cálculo de IVA y cobertura de pruebas) antes de señalar el bloqueante.
- [Pendiente: agregar fecha de aprobación final una vez que valux0
  resuelva el punto]