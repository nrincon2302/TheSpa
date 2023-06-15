/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';

import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { ArticuloDetail } from '../articulo-detail';
import { ArticuloDetailComponent } from './articulo-detail.component';
import { Sede } from 'src/app/sede/sede';

describe('ArticuloDetailComponent', () => {
  let component: ArticuloDetailComponent;
  let fixture: ComponentFixture<ArticuloDetailComponent>;
  let debug: DebugElement;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ArticuloDetailComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticuloDetailComponent);
    component = fixture.componentInstance;

    let sede = new Sede(
      faker.datatype.number(),
      faker.lorem.sentence(),
      faker.image.imageUrl()
    );

    component.articuloDetail = new ArticuloDetail(
      faker.datatype.number(),
      faker.lorem.words(5),
      faker.lorem.words(10),
      faker.datatype.number(),
      faker.image.imageUrl(),
      faker.datatype.number(),
      faker.lorem.words(10),
      faker.datatype.number(),
      sede
    );

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have an articulo', () => {
    expect(component.articuloDetail).toBeDefined();
  });

  it('should have an articulo name', () => {
    expect(component.articuloDetail.nombre).toBeDefined();
  });

  it('should have an articulo description', () => {
    expect(component.articuloDetail.descripcion).toBeDefined();
  });

  it('should have an articulo precio', () => {
    expect(component.articuloDetail.precio).toBeDefined();
  });

  it('should have an articulo imagen', () => {
    expect(component.articuloDetail.imagen).toBeDefined();
  });

  it('should have an articulo talla', () => {
    expect(component.articuloDetail.talla).toBeDefined();
  });

  it('should have an articulo color', () => {
    expect(component.articuloDetail.color).toBeDefined();
  });

  it('should have an articulo numDisponible', () => {
    expect(component.articuloDetail.numDisponible).toBeDefined();
  });

  it('should have a sede', () => {
    expect(component.articuloDetail.sede).toBeDefined();
  });

});
