import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-productos-page',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="page-container">
      <h1>Catálogo de Productos</h1>
      <p>Módulo de productos en inicialización.</p>
    </div>
  `,
  styles: [`
    .page-container { padding: 1.5rem; }
  `]
})
export class ProductosPageComponent {}
