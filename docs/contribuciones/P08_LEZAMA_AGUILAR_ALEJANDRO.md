# Evidencia de Contribución P08
**Integrante:** Alejandro Lezama Aguilar  
**Módulo y Componente Asignado:** M10 - Infraestructura PostgreSQL en Docker, Esquema DDL y ProductoJdbcRepository

## Resumen de la Contribución
Configuré la infraestructura de base de datos relacional con Docker Compose, definí el esquema DDL con restricciones de integridad (`schema.sql`), implementé la factoría desacoplada `ConexionBD` y el adaptador de persistencia `ProductoJdbcRepository` con `PreparedStatement`, garantizando la preservación del puerto hexagonal `ProductoRepository` y cobertura con pruebas de integración.

### Problema Resuelto
La arquitectura requería reemplazar la persistencia en memoria por persistencia relacional con PostgreSQL 16 sin acoplar las entidades de dominio (`Producto`, `Dinero`, `Categoria`) a APIs de infraestructura como JDBC (`java.sql.*`). Asimismo, era necesario proveer un entorno de base de datos reproducible en contenedores y proteger el sistema contra inyecciones SQL mediante consultas preparadas.

### Solución Implementada
- **`docker-compose.yml`:** Despliegue del contenedor `postgres:16-alpine` con mapeo de volumen persistente y puerto configurable (`${DB_PORT:-5432}:5432`), provisionando automáticamente el esquema inicial.
- **`src/main/resources/schema.sql`:** DDL relacional con restricciones `PRIMARY KEY`, `NOT NULL`, `CHECK (precio >= 0)`, `CHECK (existencia >= 0)` y claves foráneas con `ON DELETE CASCADE`.
- **`ConexionBD`:** Factoría centralizada de conexiones JDBC leyendo dinámicamente variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`) con fallbacks locales seguros.
- **`ProductoJdbcRepository`:** Implementación del puerto `ProductoRepository` empleando `PreparedStatement` y `try-with-resources` para inserción (`ON CONFLICT`) y consulta por código, retornando `Optional<Producto>` y mapeando objetos de valor inmutables (`Dinero`).
- **`ProductoJdbcRepositoryTest`:** Pruebas de integración automatizadas con JUnit 5 comprobando persistencia exitosa, consulta y retorno de `Optional.empty()` ante códigos inexistentes.

### Evidencias en GitHub
- **Rama:** `feature/jdbc-producto-ddl`
- **Issue:** https://github.com/maurihm/flujo-colaborativo-ventas/issues/36
- **Pull Request (PR):** https://github.com/maurihm/flujo-colaborativo-ventas/pull/37
- **Revisor Asignado:** Valeria Hernández De la Cruz
