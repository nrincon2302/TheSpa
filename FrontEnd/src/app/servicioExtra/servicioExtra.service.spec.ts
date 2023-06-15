/* tslint:disable:no-unused-variable */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, async, inject } from '@angular/core/testing';
import { ServicioExtraService } from './servicioExtra.service';

describe('Service: ServicioExtra', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ServicioExtraService]
    });
  });

  it('should ...', inject([ServicioExtraService], (service: ServicioExtraService) => {
    expect(service).toBeTruthy();
  }));
});
