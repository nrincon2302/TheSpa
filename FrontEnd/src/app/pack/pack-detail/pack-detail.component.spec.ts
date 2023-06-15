/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { PackDetailComponent } from './pack-detail.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { PackDetail } from '../pack-detail';
import { Servicio } from 'src/app/servicio/servicio';
import { Sede } from 'src/app/sede/sede';

describe('PackDetailComponent', () => {
  let component: PackDetailComponent;
  let fixture: ComponentFixture<PackDetailComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [PackDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PackDetailComponent);
    component = fixture.componentInstance;

    let testServicios: Array<Servicio> = [];

    let sede = new Sede(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.image.imageUrl()
    );

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

    component.packDetail = new PackDetail(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.datatype.number(),
      faker.image.imageUrl(),
      sede,
      testServicios
    );

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a pack', () => {
    expect(component.packDetail).toBeDefined();
  }

  );

  it('should have a name', () => {
    expect(component.packDetail.nombre).toBeDefined();
  });

  it('should have a discount', () => {
    expect(component.packDetail.descuento).toBeDefined();
  });

  it('should have an image', () => {
    expect(component.packDetail.imagen).toBeDefined();
  });

  it('should have a sede', () => {
    expect(component.packDetail.sede).toBeDefined();
  });

  it('should have services', () => {
    expect(component.packDetail.servicios).toBeDefined();
  });

});
