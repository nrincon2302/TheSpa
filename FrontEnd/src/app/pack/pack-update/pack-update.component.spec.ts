/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { PackUpdateComponent } from './pack-update.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { PackService } from '../pack.service';

describe('PackCreateComponent', () => {
  let component: PackUpdateComponent;
  let fixture: ComponentFixture<PackUpdateComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        HttpClientModule,
        RouterTestingModule,
      ],
      declarations: [PackUpdateComponent],
      providers: [PackService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PackUpdateComponent);
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
  }
  );

  it('should have an input with id descuento and with formControlName descuento ', () => {
    expect(
      debug.query(By.css('#descuento')).attributes['formControlName']
    ).toEqual('descuento');
  }
  );

  it('should have an input with id imagen and with formControlName imagen ', () => {
    expect(debug.query(By.css('#imagen')).attributes['formControlName']).toEqual(
      'imagen'
    );
  }
  );







});
