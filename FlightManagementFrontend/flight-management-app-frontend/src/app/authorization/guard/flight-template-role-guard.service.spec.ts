import { TestBed } from '@angular/core/testing';

import { FlightTemplateRoleGuardService } from './flight-template-role-guard.service';

describe('FlightTemplateRoleGuardService', () => {
  let service: FlightTemplateRoleGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlightTemplateRoleGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
