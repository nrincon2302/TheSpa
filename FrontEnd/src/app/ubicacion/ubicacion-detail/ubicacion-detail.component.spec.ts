/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { UbicacionDetailComponent } from './ubicacion-detail.component';

describe('UbicacionDetailComponent', () => {
  let component: UbicacionDetailComponent;
  let fixture: ComponentFixture<UbicacionDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UbicacionDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UbicacionDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


});
