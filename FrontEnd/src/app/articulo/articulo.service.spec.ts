/* tslint:disable:no-unused-variable */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, async, inject } from '@angular/core/testing';
import { ArticuloService } from './articulo.service';

describe('Service: Articulo', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ArticuloService]
    });
  });

  it('should ...', inject([ArticuloService], (service: ArticuloService) => {
    expect(service).toBeTruthy();
  }));
});
