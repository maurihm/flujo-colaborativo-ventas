import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ProductosService } from '../../services/productos.service';
import { ProblemDetail } from '../../models/problem-detail.model';

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.css']
})
export class ProductoFormComponent implements OnInit {
  productoForm!: FormGroup;
  guardando = false;
  mensajeExito: string | null = null;
  errorServidor: ProblemDetail | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private readonly productosService: ProductosService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.productoForm = this.fb.group({
      codigo: ['', [Validators.required, Validators.min(1)]],
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      precio: ['', [Validators.required, Validators.min(0.01)]],
      existencia: [0, [Validators.required, Validators.min(0)]],
      categoria: ['GENERAL']
    });
  }

  onSubmit(): void {
    if (this.productoForm.invalid) {
      this.productoForm.markAllAsTouched();
      return;
    }

    this.guardando = true;
    this.errorServidor = null;
    this.mensajeExito = null;

    this.productosService.crear(this.productoForm.value).subscribe({
      next: (creado) => {
        this.guardando = false;
        this.mensajeExito = `Producto "${creado.nombre}" (Código: ${creado.codigo}) registrado exitosamente.`;
        setTimeout(() => this.router.navigate(['/productos']), 1500);
      },
      error: (err: ProblemDetail) => {
        this.guardando = false;
        this.errorServidor = err;
      }
    });
  }

  esInvalido(campo: string): boolean {
    const ctrl = this.productoForm.get(campo);
    return !!(ctrl && ctrl.invalid && (ctrl.dirty || ctrl.touched));
  }
}
