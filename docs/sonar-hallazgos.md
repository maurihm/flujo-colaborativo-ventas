# Análisis Estático SonarQube y Tratamiento de Hallazgos (`v1.0-rc`)

**Proyecto:** `ventas-tcsw`  
**Versión evaluada:** `1.0.0-rc`  
**Herramienta:** SonarQube LTS Community (`http://localhost:9000`) + JaCoCo `0.8.12`

## 1. Comando Reproducible Ejecutado
```bash
mvn clean test jacoco:report
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token="$SONAR_TOKEN"
```

## 2. Resumen de Métricas y Quality Gate
- **Quality Gate:** **PASSED**
- **Bugs:** `0` (Reliability: **A**)
- **Vulnerabilidades:** `0` (Security: **A**)
- **Security Hotspots:** `0` (Security Review: **A**)
- **Code Smells:** `0` (Maintainability: **A**)
- **Cobertura JaCoCo:** `> 80%` global de instrucciones (importada desde `target/site/jacoco/jacoco.xml`).

## 3. Tabla de Tratamiento de Hallazgos y Revisiones de Seguridad

| # | Hallazgo / Revisión | Severidad / Tipo | Estado | Decisión Técnica y Tratamiento |
| :-: | :--- | :--- | :---: | :--- |
| 1 | Archivo residual `token.json` en la raíz del repositorio | Seguridad / Higiene | **CORREGIDO** | Eliminado del control de versiones (`git rm --cached token.json`) y agregado a `.gitignore` para evitar la exposición accidental de credenciales. |
| 2 | Ejecución de contenedor API como usuario `root` | Seguridad (Docker) | **PREVENIDO / CORREGIDO** | En `Dockerfile.api` se creó el grupo `appgroup` y el usuario sin privilegios `appuser` (`USER appuser`) para ejecutar el artefacto `ventas-tcsw.jar`. |
| 3 | Valores de respaldo local en `ConexionBD` y `HibernateUtil` | Seguridad / Configuración | **REVISADO Y ACOTADO** | Restringidos únicamente a entorno local de desarrollo. En contenedores Docker y GitHub Actions se inyectan dinámicamente vía variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`) y `.env.example` documenta las variables sin versionar `.env`. |
| 4 | Cobertura parcial en vistas gráficas Swing (`MainFrame`, `CatalogoPanel`, `SwingApplication`) | Cobertura / Mantenibilidad | **JUSTIFICADO** | Declarado como límite conocido de las clases visuales heredadas del Corte 2 (C02) que requieren sesión gráfica interactiva. La lógica de dominio, aplicación, API REST, persistencia y modelos Swing supera el 80% de cobertura automatizada. |
