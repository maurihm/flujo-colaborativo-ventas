import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="form-container">
      <h1>Registrar Producto</h1>
      <p>Formulario de alta de productos.</p>
    </div>
  `,
  styles: [`
    .form-container { padding: 1.5rem; }
  `]
})
export class ProductoFormComponent {}
