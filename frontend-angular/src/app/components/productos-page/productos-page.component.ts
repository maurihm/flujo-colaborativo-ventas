import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProductosService } from '../../services/productos.service';
import { Producto } from '../../models/producto.model';
import { ProblemDetail } from '../../models/problem-detail.model';
import { ProductoListComponent } from '../producto-list/producto-list.component';

type EstadoUI = 'idle' | 'loading' | 'success' | 'empty' | 'error';

@Component({
  selector: 'app-productos-page',
  standalone: true,
  imports: [CommonModule, ProductoListComponent],
  templateUrl: './productos-page.component.html',
  styleUrls: ['./productos-page.component.css']
})
export class ProductosPageComponent implements OnInit {
  estado: EstadoUI = 'idle';
  productos: Producto[] = [];
  error: ProblemDetail | null = null;

  constructor(
    private readonly productosService: ProductosService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.estado = 'loading';
    this.error = null;
    
    this.productosService.listar().subscribe({
      next: (data) => {
        this.productos = data;
        this.estado = data.length > 0 ? 'success' : 'empty';
      },
      error: (err: ProblemDetail) => {
        this.error = err;
        this.estado = 'error';
      }
    });
  }

  verDetalle(event: any): void {
    const producto = event as Producto;
    this.router.navigate(['/productos', producto.codigo]);
  }

  irANuevo(): void {
    this.router.navigate(['/productos/nuevo']);
  }

  irAVentas(): void {
    this.router.navigate(['/ventas']);
  }
}
