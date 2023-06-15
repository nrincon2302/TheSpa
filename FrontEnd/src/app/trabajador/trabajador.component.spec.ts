/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { TrabajadorComponent } from './trabajador.component';
import { TrabajadorDetail } from './trabajador-detail';
import { Servicio } from '../servicio/servicio';
import { Sede } from '../sede/sede';

describe('TrabajadorComponent', () => {
  let component: TrabajadorComponent;
  let fixture: ComponentFixture<TrabajadorComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [TrabajadorComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrabajadorComponent);
    component = fixture.componentInstance;

    let testTrabajador: Array<TrabajadorDetail> = [];
    let testServicios: Array<Servicio> = [];
    let testSedes: Array<Sede> = [];

    for (let i = 0; i < 3; i++) {
      testSedes[i] = new Sede(
        faker.datatype.number(),
        faker.lorem.sentence(),
        faker.image.imageUrl()
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

      for (let i = 0; i < 3; i++) {
        testTrabajador[i] = new TrabajadorDetail(
          faker.datatype.number(),
          faker.lorem.words(5),
          faker.image.imageUrl(),
          faker.datatype.number(),
          faker.datatype.boolean(),
          testSedes,
          testServicios

        );
      }

      component.trabajadores = testTrabajador;

      fixture.detectChanges();
      debug = fixture.debugElement;
    }
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a list of trabajadores', () => {
    expect(component.trabajadores.length).toEqual(3);
  });

  it('a trabajador should be a TrabajadorDetail', () => {
    expect(component.trabajadores[0] instanceof TrabajadorDetail).toBeTruthy();
  }
  );


  it('should have a list of sedes', () => {
    expect(component.trabajadores[0].sedes).toBeTruthy();
  }
  );

  it('should have a servicios', () => {
    expect(component.trabajadores[0].servicios).toBeTruthy();
  }
  );
});
