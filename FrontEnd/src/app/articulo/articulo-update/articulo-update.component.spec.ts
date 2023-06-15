/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ArticuloService } from '../articulo.service';
import { ArticuloUpdateComponent } from './articulo-update.component';

describe('ArticuloUpdateComponent', () => {
  let component: ArticuloUpdateComponent;
  let fixture: ComponentFixture<ArticuloUpdateComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        HttpClientModule,
        RouterTestingModule,
      ],
      declarations: [ ArticuloUpdateComponent ],
      providers: [ArticuloService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticuloUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a button with id submit and with type submit', () => {
    expect(debug.query(By.css('#submit')).attributes['type']).toEqual('submit');
  });

  it('should have an input with id nombre and with formControlName nombre ', () => {
    expect(debug.query(By.css('#nombre')).attributes['formControlName']).toEqual(
      'nombre'
    );
  });

  it('should have an input with id descripcion and with formControlName descripcion ', () => {
    expect(
      debug.query(By.css('#descripcion')).attributes['formControlName']
    ).toEqual('descripcion');
  });

  it('should have an input with id precio and with formControlName precio ', () => {
    expect(debug.query(By.css('#precio')).attributes['formControlName']).toEqual(
      'precio'
    );
  });

  it('should have an input with id imagen and with formControlName imagen ', () => {
    expect(debug.query(By.css('#imagen')).attributes['formControlName']).toEqual(
      'imagen'
    );
  });

  it('should have an input with id talla and with formControlName talla ', () => {
    expect(debug.query(By.css('#talla')).attributes['formControlName']).toEqual(
      'talla'
    );
  });

  it('should have an input with id color and with formControlName color ', () => {
    expect(debug.query(By.css('#color')).attributes['formControlName']).toEqual(
      'color'
    );
  });

  it('should have an input with id numDisponible and with formControlName numDisponible ', () => {
    expect(debug.query(By.css('#numDisponible')).attributes['formControlName']).toEqual(
      'numDisponible'
    );
  });
});
