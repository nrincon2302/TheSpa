/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { ServicioDetailComponent } from './servicio-detail.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { ServicioDetail } from '../servicio-detail';
import { Sede } from 'src/app/sede/sede';
import { Trabajador } from 'src/app/trabajador/trabajador';
import { Pack } from 'src/app/pack/pack';

describe('AuthorDetailComponent', () => {
  let component: ServicioDetailComponent;
  let fixture: ComponentFixture<ServicioDetailComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ServicioDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicioDetailComponent);
    component = fixture.componentInstance;

    let testTrabajadores: Array<Trabajador> = [];
    let testPacks: Array<Pack> = [];

    let sede = new Sede(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.image.imageUrl()
    );

    for (let i = 0; i < 2; i++) {
      testTrabajadores[i] = new Trabajador(
        faker.datatype.number(),
        faker.lorem.sentence(),
        faker.image.imageUrl(),
        faker.datatype.number(),
        faker.datatype.boolean()

      );
    }

    component.servicioDetail = new ServicioDetail(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.lorem.paragraph(),
      faker.datatype.number(),
      faker.image.imageUrl(),
      faker.datatype.number(),
      faker.lorem.paragraph(),
      faker.datatype.boolean(),
      faker.datatype.number(),
      sede,
      testTrabajadores,
      testPacks

    );

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a servicio', () => {
    expect(component.servicioDetail).toBeDefined();
  }
  );

  it('should have a name', () => {
    expect(component.servicioDetail.nombre).toBeDefined();
  }
  );

  it('should have a description', () => {
    expect(component.servicioDetail.descripcion).toBeDefined();
  }
  );

  it('should have a price', () => {
    expect(component.servicioDetail.precio).toBeDefined();
  }
  );

  it('should have a image', () => {
    expect(component.servicioDetail.imagen).toBeDefined();
  }
  );

  it('should have a duration', () => {
    expect(component.servicioDetail.duracion).toBeDefined();
  }
  );

  it('should have a description', () => {
    expect(component.servicioDetail.descripcion).toBeDefined();
  }
  );

  it('should have a available', () => {
    expect(component.servicioDetail.disponible).toBeDefined();
  }
  );

  it('should have a sede', () => {
    expect(component.servicioDetail.sede).toBeDefined();
  }
  );

  it('should have a trabajadores', () => {
    expect(component.servicioDetail.trabajador).toBeDefined();
  }
  );



});
