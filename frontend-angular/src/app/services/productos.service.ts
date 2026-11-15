import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Producto, NuevoProducto } from '../models/producto.model';
import { ProblemDetail } from '../models/problem-detail.model';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {
  private readonly baseUrl = '/api/productos';

  constructor(private readonly http: HttpClient) {}

  listar(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.baseUrl).pipe(
      catchError(this.handleError)
    );
  }

  buscarPorCodigo(codigo: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.baseUrl}/${codigo}`).pipe(
      catchError(this.handleError)
    );
  }

  crear(producto: NuevoProducto): Observable<Producto> {
    return this.http.post<Producto>(this.baseUrl, producto).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let problem: ProblemDetail;
    if (error.error && typeof error.error === 'object' && error.error.title) {
      problem = error.error as ProblemDetail;
    } else {
      problem = {
        type: 'about:blank',
        title: error.status === 0 ? 'Error de red o conexión' : error.statusText || 'Error inesperado',
        status: error.status || 500,
        detail: error.message || 'No se pudo comunicar con el servidor.',
        instance: error.url || '/api/productos'
      };
    }
    return throwError(() => problem);
  }
}
