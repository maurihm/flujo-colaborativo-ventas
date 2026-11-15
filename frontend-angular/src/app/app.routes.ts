import { Routes } from '@angular/router';
import { ProductosPageComponent } from './components/productos-page/productos-page.component';
import { ProductoFormComponent } from './components/producto-form/producto-form.component';
import { ProductoDetailComponent } from './components/producto-detail/producto-detail.component';
import { CarritoVentasComponent } from './components/carrito-ventas/carrito-ventas.component';

export const routes: Routes = [
  { path: '', redirectTo: 'productos', pathMatch: 'full' },
  { path: 'productos', component: ProductosPageComponent, title: 'Catálogo de Productos - Ventas TCSW' },
  { path: 'productos/nuevo', component: ProductoFormComponent, title: 'Registrar Producto - Ventas TCSW' },
  { path: 'productos/:codigo', component: ProductoDetailComponent, title: 'Detalle de Producto - Ventas TCSW' },
  { path: 'ventas', component: CarritoVentasComponent, title: 'Carrito de Ventas - Ventas TCSW' },
  { path: '**', redirectTo: 'productos' }
];
