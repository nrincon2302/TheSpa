/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { PackListComponent } from './pack-list.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { PackDetail } from '../pack-detail';
import { Sede } from 'src/app/sede/sede';
import { Servicio } from 'src/app/servicio/servicio';

describe('AuthorListComponent', () => {
  let component: PackListComponent;
  let fixture: ComponentFixture<PackListComponent>;
  let debug: DebugElement;

  beforeEach((() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [PackListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PackListComponent);
    component = fixture.componentInstance;

    let testPacks: Array<PackDetail> = [];
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

    for (let i = 0; i < 10; i++) {
      testPacks[i] = new PackDetail(
        faker.datatype.number(),
        faker.lorem.sentence(),
        faker.datatype.number(),
        faker.image.imageUrl(),
        sede,
        testServicios
      );
    }

    component.packs = testPacks;

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a list of packs', () => {
    expect(component.packs.length).toBeGreaterThan(0);
  });

  it('should have 10 packs', () => {
    expect(component.packs.length).toEqual(10);
  }
  );

  it('a pack should be a packDetail', () => {
    expect(component.packs[0] instanceof PackDetail).toBeTruthy();
  }
  );


});
