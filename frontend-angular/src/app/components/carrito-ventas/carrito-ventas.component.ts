import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-carrito-ventas',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="ventas-container">
      <h1>Carrito de Ventas</h1>
      <p>Módulo de ventas y partidas.</p>
    </div>
  `,
  styles: [`
    .ventas-container { padding: 1.5rem; }
  `]
})
export class CarritoVentasComponent {}
