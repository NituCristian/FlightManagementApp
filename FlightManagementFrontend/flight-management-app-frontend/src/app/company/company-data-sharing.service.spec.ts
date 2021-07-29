import { TestBed } from '@angular/core/testing';

import { CompanyDataSharingService } from './company-data-sharing.service';

describe('CompanyDataSharingService', () => {
  let service: CompanyDataSharingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyDataSharingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
