import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Producto } from '../../models/producto.model';

@Component({
  selector: 'app-producto-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.css']
})
export class ProductoListComponent {
  @Input() productos: Producto[] = [];
  @Output() seleccionar = new EventEmitter<Producto>();
  @Output() agregarAlCarrito = new EventEmitter<Producto>();

  onSeleccionar(p: Producto): void {
    this.seleccionar.emit(p);
  }

  onAgregar(p: Producto): void {
    this.agregarAlCarrito.emit(p);
  }
}