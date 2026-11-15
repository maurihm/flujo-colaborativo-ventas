import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductosService } from './productos.service';
import { Producto, NuevoProducto } from '../models/producto.model';
import { ProblemDetail } from '../models/problem-detail.model';

describe('ProductosService', () => {
  let service: ProductosService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductosService]
    });
    service = TestBed.inject(ProductosService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('debe solicitar GET /api/productos y retornar la lista de productos', () => {
    const mockProductos: Producto[] = [
      { codigo: 100, nombre: 'Teclado Mecanico', precio: 850.0, existencia: 15, categoria: 'GENERAL' },
      { codigo: 101, nombre: 'Mouse Gamer', precio: 450.0, existencia: 20, categoria: 'GENERAL' }
    ];

    service.listar().subscribe((data) => {
      expect(data.length).toBe(2);
      expect(data[0].codigo).toBe(100);
      expect(data[0].nombre).toBe('Teclado Mecanico');
    });

    const req = httpMock.expectOne('/api/productos');
    expect(req.request.method).toBe('GET');
    req.flush(mockProductos);
  });

  it('debe enviar POST /api/productos con el nuevo producto y retornar 201', () => {
    const nuevo: NuevoProducto = { codigo: 205, nombre: 'Monitor 24', precio: 3200.0, existencia: 5 };
    const creado: Producto = { ...nuevo, categoria: 'GENERAL' };

    service.crear(nuevo).subscribe((res) => {
      expect(res.codigo).toBe(205);
      expect(res.nombre).toBe('Monitor 24');
    });

    const req = httpMock.expectOne('/api/productos');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(nuevo);
    req.flush(creado, { status: 201, statusText: 'Created' });
  });

  it('debe transformar error HTTP en objeto ProblemDetail estructurado', () => {
    const errorProblem: ProblemDetail = {
      type: 'https://tcsw.uv.mx/errors/not-found',
      title: 'Recurso no encontrado',
      status: 404,
      detail: 'Producto con codigo 999 no existe.',
      instance: '/api/productos/999'
    };

    service.buscarPorCodigo(999).subscribe({
      next: () => fail('Deberia fallar con 404'),
      error: (err: ProblemDetail) => {
        expect(err.status).toBe(404);
        expect(err.title).toBe('Recurso no encontrado');
        expect(err.detail).toContain('999');
      }
    });

    const req = httpMock.expectOne('/api/productos/999');
    req.flush(errorProblem, { status: 404, statusText: 'Not Found' });
  });
});
