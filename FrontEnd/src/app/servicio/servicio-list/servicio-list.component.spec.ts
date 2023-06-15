/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { ServicioListComponent } from './servicio-list.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { ServicioDetail } from '../servicio-detail';

describe('ServicioListComponent', () => {
  let component: ServicioListComponent;
  let fixture: ComponentFixture<ServicioListComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ServicioListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicioListComponent);
    component = fixture.componentInstance;

    let testService: Array<ServicioDetail> = [];

    for (let i = 0; i < 3; i++) {
      testService[i] = new ServicioDetail(
        faker.datatype.number(),
        faker.lorem.words(5),
        faker.lorem.words(10),
        faker.datatype.number(),
        faker.image.imageUrl(),
        faker.datatype.number(),
        faker.lorem.words(10),
        faker.datatype.boolean(),
        faker.datatype.number(),
        undefined as any,
        [],
        []
      );
    }

    component.servicios = testService;

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a list of servicios', () => {
    expect(component.servicios.length).toEqual(3);
  }

  );


});
