import { TestBed } from '@angular/core/testing';

import { FlightTemplateService } from './flight-template.service';

describe('FlightTemplateService', () => {
  let service: FlightTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlightTemplateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
