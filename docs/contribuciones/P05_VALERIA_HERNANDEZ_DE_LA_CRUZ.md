# REPORTE DE EVIDENCIA: PRÁCTICA P05 - ARQUITECTURA HEXAGONAL

* **Estudiante:** Valeria Hernández De la Cruz
* **Rama de Trabajo:** `feature/crear-venta`
* **Issue Relacionado:** [Implementar caso de uso: Crear Venta #13](https://github.com/maurihm/flujo-colaborativo-ventas/issues/13)
---

## 1. Objetivo de la Contribución
Implementación del caso de uso **Crear Venta** aplicando los principios de la Arquitectura Hexagonal (puertos, DTOs, servicios de aplicación y adaptadores en memoria), asegurando el aislamiento del dominio y la calidad mediante pruebas unitarias con JUnit.

---

## 2. Componentes Desarrollados
* **Capa de Aplicación (DTO y Servicio):**
  * `ComandoCrearVenta.java`: Objeto de transferencia de datos (Comando) que encapsula los datos necesarios para iniciar la venta con el cliente.
  * `CrearVentaService.java`: Servicio de aplicación que implementa el puerto de entrada `CrearVentaUseCase`, gestionando la lógica para instanciar la venta en estado `NUEVA` y delegar su persistencia.
* **Puertos (Entrada y Salida):**
  * `CrearVentaUseCase.java`: Interfaz del puerto de entrada.
  * `VentaRepository.java`: Interfaz del puerto de salida para la persistencia.
* **Adaptadores:**
  * `InMemoryVentaRepository.java`: Adaptador de salida que simula una base de datos temporal utilizando un mapa en memoria (`HashMap`).
* **Pruebas Unitarias:**
  * `CrearVentaServiceTest.java`: Pruebas unitarias con JUnit 5 para validar el correcto funcionamiento del servicio de creación de ventas.

---

## 3. Evidencias de Ejecución (Build Success)
* Se verificó la correcta compilación del proyecto y la ejecución exitosa de las pruebas unitarias mediante Maven (`mvn test`), obteniendo estado `BUILD SUCCESS`.
