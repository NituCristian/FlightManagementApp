import { TestBed } from '@angular/core/testing';

import { AirportGuardService } from './airport-guard.service';

describe('AirportGuardService', () => {
  let service: AirportGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirportGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
