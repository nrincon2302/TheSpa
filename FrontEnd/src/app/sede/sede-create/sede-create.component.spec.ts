/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { SedeCreateComponent } from './sede-create.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { SedeService } from '../sede.service';

describe('ServicioCreateComponent', () => {
  let component: SedeCreateComponent;
  let fixture: ComponentFixture<SedeCreateComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        HttpClientModule,
        RouterTestingModule,
      ],
      declarations: [SedeCreateComponent],
      providers: [SedeService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SedeCreateComponent);
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


});
