/* tslint:disable:no-unused-variable */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, inject } from '@angular/core/testing';
import { SedeService } from './sede.service';

describe('Service: Sede', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SedeService]
    });
  });

  it('should ...', inject([SedeService], (sede: SedeService) => {
    expect(sede).toBeTruthy();
  }));
});
