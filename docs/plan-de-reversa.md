# Plan Operativo de Reversa (`v1.0-rc` → `v0.3-web`)

## 1. Compatibilidad de Datos
La versión `v1.0-rc` no introduce migraciones destructivas sobre `src/main/resources/schema.sql`. Las tablas `producto`, `venta` y `detalle_venta` son 100% compatibles hacia atrás con la versión estable anterior `v0.3-web`.

## 2. Procedimiento de Reversa Ejecutado y Comprobado

### Paso 1: Respaldo previo de PostgreSQL antes de revertir
```bash
docker exec ventas-postgres pg_dump -U ventas_user -d ventas_tcsw > /tmp/backup_pre_reversa.sql
```

### Paso 2: Detener servicios web/API de `v1.0-rc` conservando la base de datos
```bash
docker compose stop frontend api
```

### Paso 3: Revertir el código a la etiqueta estable anterior (`v0.3-web`)
```bash
git checkout v0.3-web
```

### Paso 4: Verificar disponibilidad de la API `v0.3-web` sobre PostgreSQL (200 OK)
```bash
mvn spring-boot:run
# En otra terminal:
curl -i http://localhost:8080/productos
```

### Paso 5: Restauración limpia a `main` / `v1.0-rc` tras comprobar la reversa
```bash
git checkout main
docker compose up -d --build
curl -i http://localhost:4200/api/productos
```

## 3. Resultado de la Prueba
- Respaldo generado exitosamente en `/tmp/backup_pre_reversa.sql`.
- Retorno a `v0.3-web` operativo respondiendo `HTTP/1.1 200` en `/productos` sin pérdida de registros.
- Restauración a `v1.0-rc` verificada en `http://localhost:4200/api/productos` con `HTTP/1.1 200`.
