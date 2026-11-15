import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-producto-detail',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="detail-container">
      <h1>Detalle de Producto</h1>
      <p>Información del producto.</p>
    </div>
  `,
  styles: [`
    .detail-container { padding: 1.5rem; }
  `]
})
export class ProductoDetailComponent {}
