# UNIVERSIDAD VERACRUZANA
## Tecnologías para la Construcción de Software (TCSW-19234)
# C02_EQUIPO_01
Evidencia de Equipo: Actividad C02 (Corte 2: Escritorio y Persistencia)

**Módulos Integrados:** M08 y M09 (Cliente Swing) + M10 y M11 (Persistencia Relacional y Transacciones)
**Instrumento de Evaluación:** R03 (7.00 puntos)
**Repositorio Oficial:** https://github.com/maurihm/flujo-colaborativo-ventas
**Rama de Entrega:** main
**Etiqueta Oficial del Corte:** `v0.2-escritorio`
**Integrantes (Equipo 01):**
1. Alejandro Lezama Aguilar
2. Valeria Hernández De la Cruz
3. Mauricio Hernández Martínez
4. Axel Uriel García Loyo

## 1. Índice de Evidencias por Criterio de la Rúbrica R03
| Criterio R03 | Puntaje | Estado | Evidencias en el Repositorio | Descripción y Justificación |
| --- | --- | --- | --- | --- |
| **1. Swing y MVC** | **2.0 pts** | VERIFICADO | `SwingApplication.java` <br> `VentaSwingController.java` <br> `ProductoTableModel.java` <br> `SwingPersistenciaIntegrationTest.java` | Cliente gráfico Swing conectado a los adaptadores de base de datos PostgreSQL. Separación estricta MVC en el EDT. Demostración de persistencia antes y después del reinicio de la aplicación con catálogo sincronizado. |
| **2. Hibernate y PostgreSQL** | **2.0 pts** | VERIFICADO | `HibernateUtil.java` <br> `entity/ProductoEntity.java` <br> `entity/VentaEntity.java` <br> `entity/DetalleVentaEntity.java` <br> `ProductoHibernateRepository.java`<br> `VentaHibernateRepository.java` | Evolución del adaptador de persistencia a Hibernate 5.6 sobre PostgreSQL 16 sin credenciales en código duro. Mapeo relacional de entidades JPA manteniendo el núcleo de dominio puro sin dependencias externas. |
| **3. Consistencia Transaccional** | **1.5 pts** | VERIFICADO | `VentaHibernateRepository.java` <br> `TransaccionHibernateVentaTest.java` <br> `TransaccionHibernateRollbackTest.java` | Unidad de negocio de venta coordinada en una sola transacción (`session.beginTransaction`). Guardado de encabezado, partidas e inventario con confirmación atómica (`commit`) y reversión probada (`rollback`) ante fallos controlados. |
| **4. Pruebas y Retest** | **1.0 pt** | VERIFICADO | `src/test/java/**` (64 pruebas) <br> `ArchitectureTest.java` (ArchUnit)<br>`pom.xml` (JaCoCo) | Suite integral con JUnit 5 ejecutada con BUILD SUCCESS: pruebas unitarias, de interfaz Swing, de integración JDBC, de integración Hibernate y pruebas de arquitectura que certifican independencia del núcleo. |
| **5. Colaboración** | **0.5 pts** | VERIFICADO | Historial Git en main <br>Etiqueta v0.2-escritorio <br> Issues #44, #46, #48, #50<br>PRs #45, #47, #49, #51 <br>`docs/contribuciones/C02_*.md` | Flujo colaborativo secuencial con ramas por funcionalidad, Pull Requests revisados entre pares, trazabilidad completa de autoría y análisis Sonar sin vulnerabilidades ni bugs. |

### Declaración de Estados de Verificación
- **VERIFICADO:** Integración Swing-PostgreSQL, ORM Hibernate, entidades JPA, transacciones atómicas con rollback, 64 pruebas en verde con `mvn clean test`, reglas ArchUnit y tag `v0.2-escritorio`.
- **NO_VERIFICADO:** Ninguno.
- **PENDIENTE:** Ninguno.