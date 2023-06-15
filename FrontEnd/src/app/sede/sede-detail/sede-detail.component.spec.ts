/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { SedeDetailComponent } from './sede-detail.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { SedeDetail } from '../sede-detail';
import { ServicioExtra } from 'src/app/servicioExtra/servicioExtra';
import { Articulo } from 'src/app/articulo/articulo';
import { Servicio } from 'src/app/servicio/servicio';
import { Pack } from 'src/app/pack/pack';
import { Trabajador } from 'src/app/trabajador/trabajador';
import { Ubicacion } from 'src/app/ubicacion/ubicacion';

describe('SedeDetailComponent', () => {
  let component: SedeDetailComponent;
  let fixture: ComponentFixture<SedeDetailComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [SedeDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SedeDetailComponent);
    component = fixture.componentInstance;

    let testTrabajadores: Array<Trabajador> = [];
    let testPacks: Array<Pack> = [];
    let testServicios: Array<Servicio> = [];
    let testArticulos: Array<Articulo> = [];
    let testServiciosExtra: Array<ServicioExtra> = [];

    let ubicacion = new Ubicacion(
      faker.datatype.number(),
      faker.datatype.number(),
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.lorem.sentence()
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

    for (let i = 0; i < 2; i++) {
      testPacks[i] = new Pack(
        faker.datatype.number(),
        faker.lorem.sentence(),
        faker.datatype.number(),
        faker.image.imageUrl(),
      );
    }

    for (let i = 0; i < 2; i++) {
      testServicios[i] = new Servicio(
        faker.datatype.number(),
        faker.lorem.sentence(),
        faker.lorem.paragraph(),
        faker.datatype.number(),
        faker.image.imageUrl(),
        faker.datatype.number(),
        faker.lorem.paragraph(),
        faker.datatype.boolean(),
        faker.datatype.number()
      );
    }

    for (let i = 0; i < 2; i++) {
      testArticulos[i] = new Articulo(
        faker.datatype.number(),
        faker.lorem.sentence(),
        faker.lorem.paragraph(),
        faker.datatype.number(),
        faker.image.imageUrl(),
        faker.datatype.number(),
        faker.lorem.paragraph(),
        faker.datatype.number()
      );
    }

    for (let i = 0; i < 2; i++) {
      testServiciosExtra[i] = new ServicioExtra(
        faker.datatype.number(),
        faker.lorem.sentence(),
        faker.lorem.paragraph(),
        faker.datatype.number(),
        faker.image.imageUrl(),
        faker.datatype.boolean(),
      );
    }

    component.sedeDetail = new SedeDetail(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.lorem.paragraph(),
      testTrabajadores,
      testServiciosExtra,
      testArticulos,
      testServicios,
      testPacks,
      ubicacion
    );





    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a name', () => {
    expect(component.sedeDetail.nombre).toBeDefined();
  });

  it('should have an image', () => {
    expect(component.sedeDetail.imagen).toBeDefined();
  });

  it('should have a list of trabajadores', () => {
    expect(component.sedeDetail.trabajadores).toBeDefined();

  });

  it('should have a list of servicios extra', () => {
    expect(component.sedeDetail.serviciosExtra).toBeDefined();
  });

  it('should have a list of articulos', () => {
    expect(component.sedeDetail.articulos).toBeDefined();
  });

  it('should have a list of servicios', () => {
    expect(component.sedeDetail.servicios).toBeDefined();
  });

  it('should have a list of packs', () => {
    expect(component.sedeDetail.packsDeServicios).toBeDefined();
  });

  it('should have a ubicacion', () => {
    expect(component.sedeDetail.ubicacion).toBeDefined();
  });







});
