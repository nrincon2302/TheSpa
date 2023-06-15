/* tslint:disable:no-unused-variable */

import { TestBed, inject } from '@angular/core/testing';
import { PackService } from './pack.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';


describe('Service: Pack', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PackService]
    });
  });

  it('should ...', inject([PackService], (service: PackService) => {
    expect(service).toBeTruthy();
  }));
});
