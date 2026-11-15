export interface ItemVenta {
  codigoProducto: number;
  cantidad: number;
}

export interface CrearVentaRequest {
  clienteNombre: string;
  clienteEmail: string;
  items: ItemVenta[];
}

export interface VentaResponse {
  id: string;
  cliente: string;
  total: number;
  estado: string;
  detallesCount: number;
}

export interface ItemCarrito {
  codigoProducto: number;
  nombre: string;
  precioUnitario: number;
  cantidad: number;
  subtotal: number;
}
