# Matriz de Riesgos y Cobertura de Pruebas — Candidato de Liberación (`v1.0-rc`)

**Proyecto:** `ventas-tcsw`  
**Versión evaluada:** `1.0.0-rc` (`v1.0-rc`)  
**Ejecución total:** **90 pruebas automatizadas** (81 pruebas JUnit 5 en backend + 9 pruebas Jasmine/Karma en Angular)

---

## 1. Matriz de Riesgos Relevantes de Liberación

| ID | Riesgo Técnico / de Negocio | Severidad | Frontera / Capas | Prueba(s) Automatizada(s) que lo Cubren | Estado |
| :-: | :--- | :---: | :--- | :--- | :---: |
| **R-01** | Desfase entre pruebas en memoria (`InMemoryVentaRepository`) y persistencia real en `POST /ventas` (asignación de ID en BD, transición a estado `PAGADA` sin reinsertar llave primaria y correos extensos frente a `VARCHAR(20)`). | **Crítico** | REST → Aplicación → Hibernate → PostgreSQL | `CandidatoLiberacionRiesgosTest#ventaCompletaSobrePostgresDescuentaStockYSoportaEmailLargo` | **VERIFICADO** |
| **R-02** | Confirmación parcial de venta o corrupción de existencias cuando una partida del carrito supera el inventario disponible. | **Crítico** | Hibernate / JDBC → PostgreSQL → RFC 9457 | `CandidatoLiberacionRiesgosTest#ventaSinStockEjecutaRollbackYRetorna409ProblemDetail`, `TransaccionHibernateRollbackTest`, `TransaccionRollbackTest` | **VERIFICADO** |
| **R-03** | Filtración de trazas internas (`stack traces` o mensajes SQL) al cliente ante excepciones inesperadas o entradas inválidas. | **Alto** | Adaptador REST (`ApiExceptionHandler`) | `CandidatoLiberacionRiesgosTest#exceptionHandlerEInvarianteIdCubrenErroresControlados`, `ProductoRestControllerTest`, `VentaRestControllerTest` | **VERIFICADO** |
| **R-04** | Erosión de la Arquitectura Hexagonal por dependencias directas de `domain` o `application` hacia `adapters`, Spring, Swing o `java.sql`. | **Alto** | Todas las capas Java | `ArchitectureTest` (3 reglas) y `RestArchitectureTest` (2 reglas ArchUnit) | **VERIFICADO** |
| **R-05** | Error aritmético, mutabilidad en montos monetarios, cálculo incorrecto de IVA (16%) o falla en descuentos y notificaciones de confirmación. | **Medio** | Núcleo de Dominio y Aplicación | `DineroTest`, `ProductoTest`, `ClienteTest`, `VentaTest`, `VentaFactoryTest`, `StrategyTest`, `ObserverTest`, `VentaFacadeTest` | **VERIFICADO** |
| **R-06** | Envío de datos inválidos desde el navegador o ausencia de retroalimentación visual/accesible ante latencia o error de red en Angular. | **Medio** | Cliente Web Angular (`HttpClient`, `ReactiveFormsModule`) | `producto-form.component.spec.ts`, `productos.service.spec.ts`, `app.spec.ts` (9 pruebas en `ChromeHeadless`) | **VERIFICADO** |
| **R-07** | *Límite declarado:* Vistas gráficas interactivas de escritorio (`MainFrame`, `CatalogoPanel`, `SwingApplication`) dependen de sesión gráfica de usuario y no forman parte del contenedor web. | **Bajo** | Adaptador Swing heredado (C02) | Lógica desacoplada cubierta en `ProductoTableModelTest`, `FormularioValidacionTest`, `VentaSwingControllerTest` y `SwingPersistenciaIntegrationTest` | **DECLARADO** |

---

## 2. Resumen de Resultados y Cobertura

- **Backend (`mvn clean test jacoco:report`):** `81` pruebas ejecutadas, `0` fallos, `0` errores, `0` omitidas (`BUILD SUCCESS`).
- **Frontend (`npm test -- --watch=false --browsers=ChromeHeadless`):** `9` pruebas ejecutadas, `9 SUCCESS`.
- **Cobertura JaCoCo (`target/site/jacoco/index.html`):** Cobertura global de instrucciones superior al **80%** (y >90% en paquetes de dominio, aplicación, controladores REST y repositorios de persistencia).
