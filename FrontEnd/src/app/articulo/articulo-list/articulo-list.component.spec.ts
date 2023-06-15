/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ArticuloListComponent } from './articulo-list.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import faker from '@faker-js/faker';
import { ArticuloDetail } from '../articulo-detail';

describe('ArticuloListComponent', () => {
  let component: ArticuloListComponent;
  let fixture: ComponentFixture<ArticuloListComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ArticuloListComponent]
    })
      .compileComponents();
  }));


  beforeEach(() => {
    fixture = TestBed.createComponent(ArticuloListComponent);
    component = fixture.componentInstance;

    let testService: Array<ArticuloDetail> = [];

    for (let i = 0; i < 3; i++) {
      testService[i] = new ArticuloDetail(
        faker.datatype.number(),
        faker.lorem.words(5),
        faker.lorem.words(10),
        faker.datatype.number(),
        faker.lorem.words(5),
        faker.datatype.number(),
        faker.lorem.words(5),
        faker.datatype.number(),
        undefined as any
      );
    }

    component.articulosDeRopa = testService;

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a list of articulos de ropa', () => {
    expect(component.articulosDeRopa.length).toEqual(3);
  }

  );
});
