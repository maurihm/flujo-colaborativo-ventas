CREATE TABLE IF NOT EXISTS producto (
    codigo BIGINT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio NUMERIC(12, 2) NOT NULL CHECK (precio >= 0),
    existencia INTEGER NOT NULL CHECK (existencia >= 0),
    categoria VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS venta (
    id VARCHAR(50) PRIMARY KEY,
    cliente_codigo BIGINT NOT NULL,
    cliente_nombre VARCHAR(100) NOT NULL,
    cliente_apellido VARCHAR(100) NOT NULL,
    cliente_telefono VARCHAR(20) NOT NULL,
    total NUMERIC(12, 2) NOT NULL CHECK (total >= 0),
    estado VARCHAR(30) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS detalle_venta (
    id SERIAL PRIMARY KEY,
    venta_id VARCHAR(50) NOT NULL REFERENCES venta(id) ON DELETE CASCADE,
    producto_codigo BIGINT NOT NULL REFERENCES producto(codigo),
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(12, 2) NOT NULL CHECK (precio_unitario >= 0),
    subtotal NUMERIC(12, 2) NOT NULL CHECK (subtotal >= 0)
);
