/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SedeListComponent } from './sede-list.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import faker from '@faker-js/faker';
import { ServicioDetail } from 'src/app/servicio/servicio-detail';
import { ServicioListComponent } from 'src/app/servicio/servicio-list/servicio-list.component';
import { SedeDetail } from '../sede-detail';
import { PackDetail } from 'src/app/pack/pack-detail';
import { ServicioExtraDetail } from 'src/app/servicioExtra/servicioExtra-detail';
import { ArticuloDetail } from 'src/app/articulo/articulo-detail';
import { Ubicacion } from 'src/app/ubicacion/ubicacion';
import { TrabajadorDetail } from 'src/app/trabajador/trabajador-detail';

describe('SedeListComponent', () => {
  let component: SedeListComponent;
  let fixture: ComponentFixture<SedeListComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [SedeListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SedeListComponent);
    component = fixture.componentInstance;

    let testSedes: Array<SedeDetail> = [];

    let testServicios: Array<ServicioDetail> = [];
    let testPacks: Array<PackDetail> = [];
    let testServicioExtra: Array<ServicioExtraDetail> = [];
    let testArticulos: Array<ArticuloDetail> = [];
    let testTrabajadores: Array<TrabajadorDetail> = [];
    let testUbicacion: Ubicacion = new Ubicacion(
      faker.datatype.number(),
      faker.datatype.number(),
      faker.datatype.number(),
      faker.datatype.string(),
      faker.datatype.string(),
    );

    for (let i = 0; i < 3; i++) {
      testSedes[i] = new SedeDetail(
        faker.datatype.number(),
        faker.lorem.words(5),
        faker.image.imageUrl(),
        testTrabajadores,
        testServicioExtra,
        testArticulos,
        testServicios,
        testPacks,
        testUbicacion
      );

    }





    component.sedes = testSedes;

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a list of sedes', () => {
    expect(component.sedes.length).toEqual(3);
  }

  );

  it('should have a list of servicios', () => {
    expect(component.sedes[0].servicios.length).toEqual(0);
  }

  );

  it('should have a list of packs', () => {
    expect(component.sedes[0].packsDeServicios.length).toEqual(0);
  }

  );

  it('should have a list of servicios extra', () => {
    expect(component.sedes[0].serviciosExtra.length).toEqual(0);
  }

  );

  it('should have a list of articulos', () => {
    expect(component.sedes[0].articulos.length).toEqual(0);
  }

  );

  it('should have a list of trabajadores', () => {
    expect(component.sedes[0].trabajadores.length).toEqual(0);
  }

  );

  it('should have a ubicacion', () => {
    expect(component.sedes[0].ubicacion).toBeTruthy();

  }

  );






});
