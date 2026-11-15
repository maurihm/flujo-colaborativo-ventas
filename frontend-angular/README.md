# Frontend Angular — Sistema de Ventas TCSW

Cliente web desarrollado con Angular 20 para el prototipo de punto de venta del curso Tecnologías para la Construcción de Software (TCSW-19234), Universidad Veracruzana.

## Requisitos Previos

- Node.js >= 18.x (verificado con Node v22.22.0)
- npm >= 9.x
- Angular CLI >= 18.x / 20.x (`@angular/cli`)
- Backend Spring Boot ejecutándose en `http://localhost:8080`

## Instalación de Dependencias

```bash
cd frontend-angular
npm install
```

## Servidor de Desarrollo con Proxy

Para ejecutar la aplicación con el proxy inverso hacia la API REST (`/api` hacia `http://localhost:8080`):

```bash
npm start
```

La aplicación estará disponible en `http://localhost:4200/`.

## Compilación para Producción

```bash
npm run build
```

Los artefactos optimizados se generarán en el directorio `dist/frontend-angular`.

## Pruebas Unitarias

Para ejecutar las pruebas unitarias:

```bash
npm test -- --watch=false
```

## Estructura de Rutas

- `/productos`: Catálogo general de productos con estados asíncronos.
- `/productos/nuevo`: Formulario reactivo para alta de productos.
- `/productos/:codigo`: Detalle específico de un producto.
- `/ventas`: Carrito de compras y registro de ventas.
