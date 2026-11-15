import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProductosService } from '../../services/productos.service';
import { Producto } from '../../models/producto.model';
import { ProblemDetail } from '../../models/problem-detail.model';

@Component({
  selector: 'app-producto-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <section class="detail-container" aria-labelledby="titulo-detalle">
      <header class="detail-header">
        <h1 id="titulo-detalle">Detalle del Producto</h1>
        <a routerLink="/productos" class="btn btn-secondary">Volver al Catálogo</a>
      </header>

      <div *ngIf="cargando" class="estado-caja loading" role="status" aria-live="polite">
        <p>Cargando información del producto...</p>
      </div>

      <div *ngIf="error" class="alerta error" role="alert" aria-live="assertive">
        <h3>Error al consultar producto</h3>
        <p><strong>{{ error.title }}:</strong> {{ error.detail }}</p>
        <a routerLink="/productos" class="btn btn-secondary">Regresar</a>
      </div>

      <div *ngIf="!cargando && !error && producto" class="card-detalle">
        <div class="detalle-fila">
          <span class="label">Código:</span>
          <span class="valor"><strong>{{ producto.codigo }}</strong></span>
        </div>
        <div class="detalle-fila">
          <span class="label">Nombre:</span>
          <span class="valor">{{ producto.nombre }}</span>
        </div>
        <div class="detalle-fila">
          <span class="label">Precio Unitario:</span>
          <span class="valor precio">{{ producto.precio | currency:'USD':'symbol':'1.2-2' }}</span>
        </div>
        <div class="detalle-fila">
          <span class="label">Existencia en Almacén:</span>
          <span class="valor">
            <span class="badge" [class.badge-ok]="producto.existencia > 0" [class.badge-agotado]="producto.existencia === 0">
              {{ producto.existencia }} unidades
            </span>
          </span>
        </div>
        <div class="detalle-fila" *ngIf="producto.categoria">
          <span class="label">Categoría:</span>
          <span class="valor">{{ producto.categoria }}</span>
        </div>
      </div>
    </section>
  `,
  styles: [`
    .detail-container {
      max-width: 650px;
      margin: 0 auto;
    }
    .detail-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
      border-bottom: 2px solid #e9ecef;
      padding-bottom: 0.75rem;
    }
    .detail-header h1 {
      font-size: 1.5rem;
      color: #1b365d;
    }
    .card-detalle {
      background-color: #ffffff;
      padding: 1.75rem;
      border-radius: 8px;
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
    }
    .detalle-fila {
      display: flex;
      justify-content: space-between;
      padding: 0.75rem 0;
      border-bottom: 1px solid #f0f0f0;
      font-size: 1rem;
    }
    .detalle-fila:last-child {
      border-bottom: none;
    }
    .detalle-fila .label {
      font-weight: 600;
      color: #495057;
    }
    .detalle-fila .precio {
      font-size: 1.2rem;
      font-weight: 700;
      color: #007a33;
    }
    .badge {
      display: inline-block;
      padding: 0.25rem 0.6rem;
      border-radius: 4px;
      font-size: 0.85rem;
      font-weight: 600;
    }
    .badge-ok {
      background-color: #d4edda;
      color: #155724;
    }
    .badge-agotado {
      background-color: #f8d7da;
      color: #721c24;
    }
    .btn {
      padding: 0.5rem 1rem;
      font-size: 0.9rem;
      font-weight: 600;
      border-radius: 4px;
      border: none;
      text-decoration: none;
      cursor: pointer;
    }
    .btn-secondary {
      background-color: #6c757d;
      color: #ffffff;
    }
    .alerta.error {
      background-color: #f8d7da;
      color: #721c24;
      border: 1px solid #f5c6cb;
      padding: 1rem;
      border-radius: 6px;
    }
    .estado-caja.loading {
      padding: 2rem;
      text-align: center;
      color: #6c757d;
    }
  `]
})
export class ProductoDetailComponent implements OnInit {
  producto: Producto | null = null;
  cargando = false;
  error: ProblemDetail | null = null;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly productosService: ProductosService
  ) {}

  ngOnInit(): void {
    const codigoParam = this.route.snapshot.paramMap.get('codigo');
    if (codigoParam) {
      const codigo = Number(codigoParam);
      if (!isNaN(codigo)) {
        this.cargarProducto(codigo);
      } else {
        this.error = {
          type: 'about:blank',
          title: 'Código Inválido',
          status: 400,
          detail: 'El código proporcionado en la ruta no es numérico.',
          instance: `/productos/${codigoParam}`
        };
      }
    }
  }

  cargarProducto(codigo: number): void {
    this.cargando = true;
    this.error = null;
    this.productosService.buscarPorCodigo(codigo).subscribe({
      next: (data) => {
        this.producto = data;
        this.cargando = false;
      },
      error: (err: ProblemDetail) => {
        this.error = err;
        this.cargando = false;
      }
    });
  }
}
