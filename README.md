# ventas-tcsw

Sistema de punto de venta desarrollado para la EE **Tecnologías para la Construcción de Software (TCSW-19234)** — Universidad Veracruzana.

Esta versión consolida la **Práctica P08 (M10 + M11): Persistencia Relacional JDBC con PostgreSQL 16 y Transacciones Atómicas**, preservando la **Arquitectura Hexagonal**, los patrones de diseño GoF (Factory, Strategy, Observer, Facade) y el cliente gráfico con Java Swing + `SwingWorker`.

---

## Requisitos y versiones

- **Java:** JDK 11
- **Maven:** 3.9.x
- **Docker y Docker Compose:** Contenedor `postgres:16-alpine`
- **Git:** 2.x

Verifica tu entorno antes de continuar:

```bash
java -version
javac -version
mvn -version
docker --version
docker compose version
```

---

## Guía breve de reproducción (P08)

### 1. Clonar el repositorio

```bash
git clone https://github.com/maurihm/flujo-colaborativo-ventas.git
cd flujo-colaborativo-ventas
git checkout main
```

### 2. Iniciar PostgreSQL 16 en Docker Compose

El archivo `docker-compose.yml` levanta PostgreSQL 16 y ejecuta automáticamente el esquema DDL (`src/main/resources/schema.sql`):

```bash
docker compose up -d
```

> **Nota:** Si el puerto local `5432` ya está ocupado en tu máquina, puedes usar otro puerto configurable mediante variables de entorno:
> ```bash
> DB_PORT=5433 docker compose up -d
> export DB_URL="jdbc:postgresql://localhost:5433/ventas_tcsw"
> ```

### 3. Compilar y ejecutar todas las pruebas (`mvn clean test`)

Ejecuta las **60 pruebas automatizadas** (dominio, aplicación, arquitectura con ArchUnit, vistas Swing e integración JDBC/transacciones con PostgreSQL):

```bash
mvn clean test
```

La salida debe finalizar con:

```text
Tests run: 60, Failures: 0, Errors: 0, Skipped: 0
...
BUILD SUCCESS
```

### 4. Ejecutar la interfaz gráfica Swing (opcional)

```bash
mvn compile exec:java -Dexec.mainClass="ventas.adapters.in.swing.SwingApplication"
```

---

## Estructura del proyecto (Arquitectura Hexagonal)

```text
flujo-colaborativo-ventas/
├── pom.xml
├── docker-compose.yml
├── README.md
├── docs/
│   ├── P05-arquitectura.md
│   ├── P06-patrones.md
│   └── contribuciones/
│       ├── P08_EQUIPO_01.md
│       ├── P08_LEZAMA_AGUILAR_ALEJANDRO.md
│       ├── P08_HERNANDEZ_DE_LA_CRUZ_VALERIA.md
│       ├── P08_HERNANDEZ_MARTINEZ_MAURICIO.md
│       └── P08_GARCIA_LOYO_AXEL_URIEL.md
└── src/
    ├── main/
    │   ├── resources/
    │   │   └── schema.sql
    │   └── java/ventas/
    │       ├── domain/                  # Núcleo puro (Producto, Venta, DetalleVenta, Cliente, Dinero)
    │       ├── ports/
    │       │   ├── in/                  # Casos de uso
    │       │   └── out/                 # ProductoRepository, VentaRepository
    │       ├── application/             # Servicios de aplicación y VentaFacade
    │       └── adapters/
    │           ├── in/swing/            # Adaptador primario MVC con Swing y SwingWorker
    │           └── out/
    │               ├── memory/          # Adaptadores en memoria
    │               └── jdbc/            # ConexionBD, ProductoJdbcRepository, VentaJdbcRepository
    └── test/java/ventas/
        ├── ArchitectureTest.java        # Reglas ArchUnit
        ├── domain/                      # Pruebas unitarias del dominio
        ├── application/                 # Pruebas de casos de uso y patrones
        └── adapters/
            ├── in/swing/                # Pruebas de componentes Swing
            └── out/jdbc/                # Pruebas de integración JDBC y transacciones (M10/M11)
```

---

## Aspectos destacados de P08 (M10 + M11)

- **Esquema relacional (`src/main/resources/schema.sql`):** Tablas `producto`, `venta` y `detalle_venta` con restricciones `PRIMARY KEY`, `FOREIGN KEY ... ON DELETE CASCADE`, `NOT NULL` y `CHECK` (`precio >= 0`, `existencia >= 0`, `cantidad > 0`, `total >= 0`).
- **Prevención de SQL Injection y fugas de recursos (`M10`):** Uso estricto de `PreparedStatement` y bloques `try-with-resources` en `ProductoJdbcRepository` y `VentaJdbcRepository`.
- **Coordinación transaccional atómica (`M11`):** En `VentaJdbcRepository.guardar(Venta)`, se desactiva el auto-commit (`cn.setAutoCommit(false)`), se registra el encabezado, se insertan los detalles en `detalle_venta` y se descuenta el stock en `producto` (`WHERE codigo = ? AND existencia >= ?`). Si ocurre cualquier fallo, se invoca `cn.rollback()` garantizando cero estados parciales.
- **Trazabilidad del equipo (`R01`):** Consulta el índice completo de evidencias en [`docs/contribuciones/P08_EQUIPO_01.md`](docs/contribuciones/P08_EQUIPO_01.md).
