import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CrearVentaRequest, VentaResponse } from '../models/venta.model';
import { ProblemDetail } from '../models/problem-detail.model';

@Injectable({
  providedIn: 'root'
})
export class VentasService {
  private readonly baseUrl = '/api/ventas';

  constructor(private readonly http: HttpClient) {}

  procesarVenta(solicitud: CrearVentaRequest): Observable<VentaResponse> {
    return this.http.post<VentaResponse>(this.baseUrl, solicitud).pipe(
      catchError((error: HttpErrorResponse) => {
        let problem: ProblemDetail;
        if (error.error && error.error.title) {
          problem = error.error as ProblemDetail;
        } else {
          problem = {
            type: 'about:blank',
            title: 'Error al procesar venta',
            status: error.status || 500,
            detail: error.message || 'No se pudo completar la transacción.',
            instance: '/api/ventas'
          };
        }
        return throwError(() => problem);
      })
    );
  }
}
