import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { ProductoFormComponent } from './producto-form.component';
import { ProductosService } from '../../services/productos.service';

describe('ProductoFormComponent', () => {
  let component: ProductoFormComponent;
  let fixture: ComponentFixture<ProductoFormComponent>;
  let productosServiceSpy: jasmine.SpyObj<ProductosService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('ProductosService', ['crear']);

    await TestBed.configureTestingModule({
      imports: [ProductoFormComponent, ReactiveFormsModule, RouterTestingModule],
      providers: [{ provide: ProductosService, useValue: spy }]
    }).compileComponents();

    fixture = TestBed.createComponent(ProductoFormComponent);
    component = fixture.componentInstance;
    productosServiceSpy = TestBed.inject(ProductosService) as jasmine.SpyObj<ProductosService>;
    fixture.detectChanges();
  });

  it('debe iniciar con formulario invalido cuando los campos estan vacios', () => {
    expect(component.productoForm.valid).toBeFalse();
  });

  it('debe validar que precio no sea negativo ni cero', () => {
    const precioCtrl = component.productoForm.get('precio');
    precioCtrl?.setValue(0);
    expect(precioCtrl?.valid).toBeFalse();
    
    precioCtrl?.setValue(150.5);
    expect(precioCtrl?.valid).toBeTrue();
  });

  it('no debe enviar el servicio si el formulario es invalido', () => {
    component.onSubmit();
    expect(productosServiceSpy.crear).not.toHaveBeenCalled();
  });

  it('debe llamar al servicio cuando los datos sean validos', () => {
    component.productoForm.setValue({
      codigo: 300,
      nombre: 'Impresora Laser',
      precio: 4500.0,
      existencia: 10,
      categoria: 'GENERAL'
    });

    productosServiceSpy.crear.and.returnValue(of({
      codigo: 300,
      nombre: 'Impresora Laser',
      precio: 4500.0,
      existencia: 10,
      categoria: 'GENERAL'
    }));

    component.onSubmit();
    expect(productosServiceSpy.crear).toHaveBeenCalled();
  });
});
