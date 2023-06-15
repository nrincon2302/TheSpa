/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { ServicioExtraDetailComponent } from './servicioExtra-detail.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { ServicioExtraDetail } from '../servicioExtra-detail';
import { Sede } from 'src/app/sede/sede';


describe('AuthorDetailComponent', () => {
  let component: ServicioExtraDetailComponent;
  let fixture: ComponentFixture<ServicioExtraDetailComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ServicioExtraDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicioExtraDetailComponent);
    component = fixture.componentInstance;



    let sede = new Sede(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.image.imageUrl()
    );


    component.servicioExtraDetail = new ServicioExtraDetail(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.lorem.paragraph(),
      faker.datatype.number(),
      faker.image.imageUrl(),
      faker.datatype.boolean(),
      sede

    );

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a servicioExtra defined', () => {
    expect(component.servicioExtraDetail).toBeDefined();
  }
  );

  it('should have a id defined', () => {
    expect(component.servicioExtraDetail.id).toBeDefined();
  }
  );

  it('should have a name defined', () => {
    expect(component.servicioExtraDetail.nombre).toBeDefined();
  }
  );

  it('should have a descripcion defined', () => {
    expect(component.servicioExtraDetail.descripcion).toBeDefined();
  }
  );

  it('should have a precio defined', () => {
    expect(component.servicioExtraDetail.precio).toBeDefined();
  }
  );

  it('should have a imagen defined', () => {
    expect(component.servicioExtraDetail.imagen).toBeDefined();
  }
  );

  it('should have a disponible defined', () => {
    expect(component.servicioExtraDetail.disponible).toBeDefined();
  }
  );

  it('should have a sede defined', () => {
    expect(component.servicioExtraDetail.sede).toBeDefined();
  }
  );

});
