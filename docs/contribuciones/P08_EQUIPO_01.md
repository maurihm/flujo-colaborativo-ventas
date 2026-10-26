# UNIVERSIDAD VERACRUZANA
## Tecnologías para la Construcción de Software (TCSW-19234)

# P08_EQUIPO_01 — Evidencia de Equipo: Práctica P08 (Persistencia Relacional JDBC y Transacciones Atómicas)

- **Módulos:** M10 (Persistencia I) + M11 (Persistencia II y Transacciones)
- **Instrumento de evaluación:** R01 (2.00 puntos)
- **Repositorio:** https://github.com/maurihm/flujo-colaborativo-ventas
- **Rama de entrega:** `main`
- **Integrantes (Equipo 01):**
  1. Alejandro Lezama Aguilar
  2. Valeria Hernández De la Cruz
  3. Mauricio Hernández Martínez
  4. Axel Uriel García Loyo

---

## 1. Índice de Evidencias por Criterio de la Rúbrica R01

| Criterio R01 | Puntaje | Estado | Evidencia en el Repositorio | Descripción y Justificación |
| :--- | :---: | :---: | :--- | :--- |
| **1. Funcionamiento** | 0.8 pts | `VERIFICADO` | [`docker-compose.yml`](../../docker-compose.yml)<br>[`src/main/resources/schema.sql`](../../src/main/resources/schema.sql)<br>[`ProductoJdbcRepository.java`](../../src/main/java/ventas/adapters/out/jdbc/ProductoJdbcRepository.java)<br>[`VentaJdbcRepository.java`](../../src/main/java/ventas/adapters/out/jdbc/VentaJdbcRepository.java) | Contenedor reproducible de **PostgreSQL 16 (`postgres:16-alpine`)** con aprovisionamiento automático del esquema DDL (`producto`, `venta`, `detalle_venta`), persistencia y consulta parametrizada de productos y ventas, y coordinación transaccional atómica de confirmación de venta con descuento de inventario. |
| **2. Solución técnica** | 0.5 pts | `VERIFICADO` | [`ConexionBD.java`](../../src/main/java/ventas/adapters/out/jdbc/ConexionBD.java)<br>[`ProductoRepository.java`](../../src/main/java/ventas/ports/out/ProductoRepository.java)<br>[`VentaRepository.java`](../../src/main/java/ventas/ports/out/VentaRepository.java)<br>[`ArchitectureTest.java`](../../src/test/java/ventas/ArchitectureTest.java) | Se preserva la **Arquitectura Hexagonal**: el dominio (`ventas.domain`) permanece libre de dependencias SQL/JDBC (validado con **ArchUnit**). Se emplean `PreparedStatement` para prevenir inyección SQL, `try-with-resources` para cierre seguro de recursos, variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`) sin exponer secretos reales, y control manual de transacciones (`setAutoCommit(false)`, `commit()`, `rollback()`). |
| **3. Pruebas** | 0.3 pts | `VERIFICADO` | [`ProductoJdbcRepositoryTest.java`](../../src/test/java/ventas/adapters/out/jdbc/ProductoJdbcRepositoryTest.java)<br>[`VentaJdbcRepositoryTest.java`](../../src/test/java/ventas/adapters/out/jdbc/VentaJdbcRepositoryTest.java)<br>[`TransaccionVentaExitosaTest.java`](../../src/test/java/ventas/adapters/out/jdbc/TransaccionVentaExitosaTest.java)<br>[`TransaccionRollbackTest.java`](../../src/test/java/ventas/adapters/out/jdbc/TransaccionRollbackTest.java) | **60 pruebas automatizadas en verde (`BUILD SUCCESS`)** con JUnit 5: casos positivos de guardado/consulta y actualización atómica de stock, y casos negativos/límites como búsqueda de registros inexistentes (`Optional.empty()` / `null`) e inyección de fallo por violación de clave foránea comprobando `rollback()` sin estado parcial ni descuento erróneo de stock. |
| **4. Calidad y Sonar** | 0.2 pts | `VERIFICADO` | [`pom.xml`](../../pom.xml) (JaCoCo + Sonar)<br>`ventas.adapters.out.jdbc.*` | Análisis estático con SonarLint / SonarQube sin bugs ni vulnerabilidades, uso de constantes en pruebas, manejo explícito de excepciones preservando la causa raíz y cobertura de código generada con JaCoCo (`target/site/jacoco/jacoco.xml`). |
| **5. Git y evidencia** | 0.2 pts | `VERIFICADO` | [`README.md`](../../README.md)<br>`docs/contribuciones/P08_*.md`<br>Issues `#36`, `#38`, `#40`, `#42`<br>PRs `#37`, `#39`, `#41`, `#43` | Flujo colaborativo secuencial con ramas `feature/*`, Issues, Pull Requests revisados cruzadamente entre pares y bitácoras individuales completas en `docs/contribuciones/`. |

### Declaración explícita de estados (Guía de Estudiante P08)
- **VERIFICADO:** Esquema DDL (`schema.sql`), contenedor PostgreSQL 16 (`docker-compose.yml`), adaptadores JDBC (`ConexionBD`, `ProductoJdbcRepository`, `VentaJdbcRepository`), transacciones atómicas (`commit` / `rollback`), pruebas de integración positivas y negativas (`60/60` en verde con `mvn clean test`) y reglas arquitectónicas ArchUnit.
- **NO_VERIFICADO:** Ninguno.
- **PENDIENTE:** Ninguno.

---

## 2. Guía Breve de Reproducción

### Herramientas y versiones utilizadas
- **Java:** OpenJDK 11
- **Maven:** Apache Maven 3.9.x
- **Base de datos:** PostgreSQL 16 (`postgres:16-alpine` en Docker Compose)
- **Driver JDBC:** `org.postgresql:postgresql:42.7.3`
- **Pruebas:** JUnit Jupiter 5.10.2 + ArchUnit 1.3.0 + JaCoCo 0.8.12

### Comandos de ejecución paso a paso
1. **Clonar el repositorio y posicionarse en la rama `main`:**
   ```bash
   git clone https://github.com/maurihm/flujo-colaborativo-ventas.git
   cd flujo-colaborativo-ventas
   git checkout main
   ```

2. **Levantar el contenedor de PostgreSQL 16 con el esquema DDL inicial:**
   ```bash
   docker compose up -d
   ```
   *(Si el puerto local `5432` estuviera ocupado por otra instancia, puede levantarse con `DB_PORT=5433 docker compose up -d` y exportar `export DB_URL="jdbc:postgresql://localhost:5433/ventas_tcsw"`).*

3. **Verificar las tablas creadas en PostgreSQL (`producto`, `venta`, `detalle_venta`):**
   ```bash
   docker exec -it ventas-postgres psql -U ventas_user -d ventas_tcsw -c "\dt"
   ```

4. **Ejecutar la suite completa de pruebas (unitarias, arquitectura e integración JDBC):**
   ```bash
   mvn clean test
   ```
   **Resultado esperado:** `Tests run: 60, Failures: 0, Errors: 0, Skipped: 0` — `BUILD SUCCESS`.

---

## 3. Trazabilidad y Evidencia Individual por Integrante

| Integrante | Rol / Componente Asignado | Issue | Pull Request | Revisor | Bitácora Individual |
| :--- | :--- | :---: | :---: | :--- | :--- |
| **Alejandro Lezama Aguilar** | M10 — Infraestructura Docker, DDL (`schema.sql`), `ConexionBD` y `ProductoJdbcRepository` | [#36](https://github.com/maurihm/flujo-colaborativo-ventas/issues/36) | [#37](https://github.com/maurihm/flujo-colaborativo-ventas/pull/37) | Valeria Hernández De la Cruz | [`P08_LEZAMA_AGUILAR_ALEJANDRO.md`](P08_LEZAMA_AGUILAR_ALEJANDRO.md) |
| **Valeria Hernández De la Cruz** | M10 — Adaptador `VentaJdbcRepository` (encabezado de venta) y `VentaJdbcRepositoryTest` | [#38](https://github.com/maurihm/flujo-colaborativo-ventas/issues/38) | [#39](https://github.com/maurihm/flujo-colaborativo-ventas/pull/39) | Alejandro Lezama Aguilar | [`P08_HERNANDEZ_DE_LA_CRUZ_VALERIA.md`](P08_HERNANDEZ_DE_LA_CRUZ_VALERIA.md) |
| **Mauricio Hernández Martínez** | M11 — Transacción atómica (`setAutoCommit(false)`, `detalle_venta`, descuento de stock) y `TransaccionVentaExitosaTest` | [#40](https://github.com/maurihm/flujo-colaborativo-ventas/issues/40) | [#41](https://github.com/maurihm/flujo-colaborativo-ventas/pull/41) | Axel Uriel García Loyo | [`P08_HERNANDEZ_MARTINEZ_MAURICIO.md`](P08_HERNANDEZ_MARTINEZ_MAURICIO.md) |
| **Axel Uriel García Loyo** | M11 — Inyección de fallos, reversión con `rollback()` y `TransaccionRollbackTest` | [#42](https://github.com/maurihm/flujo-colaborativo-ventas/issues/42) | [#43](https://github.com/maurihm/flujo-colaborativo-ventas/pull/43) | Mauricio Hernández Martínez | [`P08_GARCIA_LOYO_AXEL_URIEL.md`](P08_GARCIA_LOYO_AXEL_URIEL.md) |

---

## 4. Autoevaluación (Guía Práctica P08)

- [x] Mi evidencia corresponde a P08, M10 (+ M11) y R01.
- [x] Otra persona puede reconstruir el resultado sin depender de mi equipo.
- [x] Incluí una comprobación positiva (`TransaccionVentaExitosaTest`, `ProductoJdbcRepositoryTest`, `VentaJdbcRepositoryTest`) y una negativa o límite declarado (`TransaccionRollbackTest` ante violación de FK y consultas de IDs inexistentes).
- [x] No incluí secretos reales, datos personales reales, rutas privadas ni contenido docente.
- [x] Puedo explicar decisiones, errores y contribución individual.
