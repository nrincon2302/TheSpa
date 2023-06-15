/* tslint:disable:no-unused-variable */

import { TestBed, inject } from '@angular/core/testing';
import { TrabajadorService } from './trabajador.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Trabajador', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TrabajadorService]
    });
  });

  it('should ...', inject([TrabajadorService], (service: TrabajadorService) => {
    expect(service).toBeTruthy();
  }));
});
