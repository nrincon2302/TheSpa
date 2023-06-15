/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { TrabajadorDetail } from '../trabajador-detail';
import { TrabajadorDetailComponent } from './trabajador-detail.component';
import { Sede } from 'src/app/sede/sede';
import { Servicio } from 'src/app/servicio/servicio';

describe('TrabajadorDetailComponent', () => {
  let component: TrabajadorDetailComponent;
  let fixture: ComponentFixture<TrabajadorDetailComponent>;
  let debug: DebugElement;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [TrabajadorDetailComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TrabajadorDetailComponent);
    component = fixture.componentInstance;

    let testServicios: Array<Servicio> = [];
    let testSedes: Array<Sede> = [];

    for (let i = 0; i < 2; i++) {
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
    }

    component.trabajadorDetail = new TrabajadorDetail(
      faker.datatype.number(),
      faker.lorem.words(5),
      faker.lorem.words(10),
      faker.datatype.number(),
      faker.datatype.boolean(),
      testSedes,
      testServicios


    );

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have an trabajador', () => {
    expect(component.trabajadorDetail).toBeDefined();
  });

  it('should have an trabajador name', () => {
    expect(component.trabajadorDetail.nombre).toBeDefined();
  });

  it('should have an trabajador foto', () => {
    expect(component.trabajadorDetail.foto).toBeDefined();
  }

  );

  it('should have an trabajador id', () => {
    expect(component.trabajadorDetail.id).toBeDefined();

  }
  );

  it('should have an trabajador calificacion', () => {
    expect(component.trabajadorDetail.calificacion).toBeDefined();
  }
  );

  it('should have an trabajador enHallOfFame', () => {
    expect(component.trabajadorDetail.enHallOfFame).toBeDefined();
  }
  );

});
