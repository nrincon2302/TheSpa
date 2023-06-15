/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ServicioExtraListComponent } from './servicioExtra-list.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import faker from '@faker-js/faker';
import { ServicioExtra } from '../servicioExtra';

describe('ServicioExtraListComponent', () => {
  let component: ServicioExtraListComponent;
  let fixture: ComponentFixture<ServicioExtraListComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ServicioExtraListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicioExtraListComponent);
    component = fixture.componentInstance;

    let testService: Array<ServicioExtra> = [];

    for (let i = 0; i < 3; i++) {
      testService[i] = new ServicioExtra(
        faker.datatype.number(),
        faker.lorem.words(5),
        faker.lorem.words(10),
        faker.datatype.number(),
        faker.lorem.words(5),
        faker.datatype.boolean()
      );
    }

    component.serviciosExtra = testService;

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a list of serviciosExtra', () => {
    expect(component.serviciosExtra.length).toEqual(3);
  }

  );
});
