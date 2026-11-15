import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ProductosService } from '../../services/productos.service';
import { VentasService } from '../../services/ventas.service';
import { Producto } from '../../models/producto.model';
import { CrearVentaRequest, ItemCarrito, VentaResponse } from '../../models/venta.model';
import { ProblemDetail } from '../../models/problem-detail.model';

@Component({
  selector: 'app-carrito-ventas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './carrito-ventas.component.html',
  styleUrls: ['./carrito-ventas.component.css']
})
export class CarritoVentasComponent implements OnInit {
  productos: Producto[] = [];
  cargandoProductos = false;

  productoSeleccionadoCodigo: number | null = null;
  cantidadSeleccionada = 1;

  carrito: ItemCarrito[] = [];

  clienteNombre = '';
  clienteEmail = '';

  procesandoVenta = false;
  ventaExitosa: VentaResponse | null = null;
  errorVenta: ProblemDetail | null = null;
  mensajeValidacion: string | null = null;

  constructor(
    private readonly productosService: ProductosService,
    private readonly ventasService: VentasService
  ) {}

  ngOnInit(): void {
    this.cargarCatalogo();
  }

  cargarCatalogo(): void {
    this.cargandoProductos = true;
    this.productosService.listar().subscribe({
      next: (data) => {
        this.productos = data;
        this.cargandoProductos = false;
        if (data.length > 0 && !this.productoSeleccionadoCodigo) {
          this.productoSeleccionadoCodigo = data[0].codigo;
        }
      },
      error: () => {
        this.cargandoProductos = false;
      }
    });
  }

  agregarItem(): void {
    this.mensajeValidacion = null;
    if (!this.productoSeleccionadoCodigo || this.cantidadSeleccionada < 1) {
      this.mensajeValidacion = 'Seleccione un producto y una cantidad válida (al menos 1).';
      return;
    }

    const producto = this.productos.find(p => p.codigo === Number(this.productoSeleccionadoCodigo));
    if (!producto) {
      this.mensajeValidacion = 'El producto seleccionado no existe en el catálogo.';
      return;
    }

    const itemExistente = this.carrito.find(i => i.codigoProducto === producto.codigo);
    if (itemExistente) {
      itemExistente.cantidad += this.cantidadSeleccionada;
      itemExistente.subtotal = itemExistente.cantidad * itemExistente.precioUnitario;
    } else {
      this.carrito.push({
        codigoProducto: producto.codigo,
        nombre: producto.nombre,
        precioUnitario: producto.precio,
        cantidad: this.cantidadSeleccionada,
        subtotal: producto.precio * this.cantidadSeleccionada
      });
    }

    this.cantidadSeleccionada = 1;
  }

  eliminarItem(codigo: number): void {
    this.carrito = this.carrito.filter(i => i.codigoProducto !== codigo);
  }

  vaciarCarrito(): void {
    this.carrito = [];
  }

  get totalVenta(): number {
    return this.carrito.reduce((acc, item) => acc + item.subtotal, 0);
  }

  confirmarVenta(): void {
    this.mensajeValidacion = null;
    this.errorVenta = null;
    this.ventaExitosa = null;

    if (this.carrito.length === 0) {
      this.mensajeValidacion = 'El carrito está vacío. Agregue al menos un producto.';
      return;
    }

    if (!this.clienteNombre.trim() || !this.clienteEmail.trim()) {
      this.mensajeValidacion = 'Ingrese el nombre y correo electrónico del cliente.';
      return;
    }

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!regexEmail.test(this.clienteEmail.trim())) {
      this.mensajeValidacion = 'El formato de correo electrónico no es válido.';
      return;
    }

    const solicitud: CrearVentaRequest = {
      clienteNombre: this.clienteNombre.trim(),
      clienteEmail: this.clienteEmail.trim(),
      items: this.carrito.map(item => ({
        codigoProducto: item.codigoProducto,
        cantidad: item.cantidad
      }))
    };

    this.procesandoVenta = true;
    this.ventasService.procesarVenta(solicitud).subscribe({
      next: (resp) => {
        this.procesandoVenta = false;
        this.ventaExitosa = resp;
        this.carrito = [];
        this.clienteNombre = '';
        this.clienteEmail = '';
      },
      error: (err: ProblemDetail) => {
        this.procesandoVenta = false;
        this.errorVenta = err;
      }
    });
  }
}
