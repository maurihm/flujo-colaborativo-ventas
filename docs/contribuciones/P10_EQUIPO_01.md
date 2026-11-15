# UNIVERSIDAD VERACRUZANA
## Tecnologías para la Construcción de Software (TCSW-19234)
# P10_EQUIPO_01
Evidencia de Equipo: Práctica P10 (Cliente Angular)

**Módulo Integrado:** M13 (Angular básico)
**Instrumento de Evaluación:** R01 (2.0 puntos)
**Repositorio Oficial:** https://github.com/maurihm/flujo-colaborativo-ventas
**Rama de Entrega:** `main`
**Etiqueta Oficial:** `v0.4-cliente-angular`
**Integrantes (Equipo 01):**
1. Alejandro Lezama Aguilar
2. Valeria Hernández De la Cruz
3. Mauricio Hernández Martínez
4. Axel Uriel García Loyo

## Índice de Evidencias y Trazabilidad

| Integrante | PR y Rama | Descripción de la Contribución | Estado |
| :--- | :--- | :--- | :--- |
| **Alejandro Lezama** | PR #65 `feature/p10-angular-setup-productos-service` | Setup Angular, enrutamiento con títulos accesibles, proxy de desarrollo, modelos OpenAPI y servicio `ProductosService` | VERIFICADO |
| **Valeria Hernández** | PR #67 `feature/p10-catalogo-productos-estados-ui` | Componentes `ProductosPageComponent`, `ProductoListComponent`, `ProductoDetailComponent` y soporte visual de estados asíncronos (`idle`, `loading`, `empty`, `success`, `error`). | VERIFICADO |
| **Mauricio Hernández** | PR #69 `feature/p10-formulario-reactivo-ventas` | Formulario reactivo `ProductoFormComponent` con validaciones, servicio `VentasService` y componente `CarritoVentasComponent` conectado a `POST /ventas`. | VERIFICADO |
| **Axel Uriel García** | PR #71 `feature/p10-pruebas-unitarias-sonar` | Suite de pruebas unitarias con `TestBed` y `HttpClientTestingModule`, build de producción (`ng build`), reporte SonarQube e índice consolidado. | VERIFICADO |

### Declaración de Estados de Verificación
**VERIFICADO:** Cliente Angular funcional en puerto 4200 consumiendo la API de Spring Boot en puerto 8080, suite de pruebas unitarias en verde, build de producción sin errores de TypeScript y calidad de código asegurada con 0 Code Smells y 0 Vulnerabilidades.
**NO VERIFICADO:** Ninguno.
**PENDIENTE:** Ninguno.
