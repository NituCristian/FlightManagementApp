import { TestBed } from '@angular/core/testing';

import { PlaneGuardService } from './plane-guard.service';

describe('PlaneGuardService', () => {
  let service: PlaneGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlaneGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
