# Contribución — [Alejandro Lezama Aguilar]

- Issue: #3 — Agregar clase Cliente y asociarla a Venta
- Rama: 3-agregar-clase-cliente-y-asociarla-a-venta
- Commits relevantes:
  - d0eae5c "Cierra #3: agrega clase Cliente y la asocia a Venta"
  - 13db786 "Eliminacion de .idea y target del control de versiones"
- Pull Request: [https://github.com/maurihm/flujo-colaborativo-ventas/pull/6]
- Revisión realizada/recibida: [Mauricio Hernandez Martinez]
- Pruebas ejecutadas: mvn clean test -> BUILD SUCCESS (29 tests, 0 fallos, incluye 8 de ClienteTest y 2 nuevas en VentaTest) — VERIFICADO
- Decisión técnica justificada: Se creó Cliente siguiendo el mismo patrón de validación que Producto (constructor con IllegalArgumentException para datos obligatorios inválidos). El teléfono se dejó opcional (puede ser nulo) porque no es un dato crítico para el dominio de ventas. No se modificó el constructor de Venta para no romper el trabajo en paralelo de otros integrantes; en su lugar se agregó el método asignarCliente().
