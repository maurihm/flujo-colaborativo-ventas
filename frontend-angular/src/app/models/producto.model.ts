export interface Producto {
  codigo: number;
  nombre: string;
  precio: number;
  existencia: number;
  categoria?: string;
}

export interface NuevoProducto {
  codigo: number;
  nombre: string;
  precio: number;
  existencia: number;
  categoria?: string;
}
