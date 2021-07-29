import { TestBed } from '@angular/core/testing';

import { PlaneDataSharingService } from './plane-data-sharing.service';

describe('PlaneDataSharingService', () => {
  let service: PlaneDataSharingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlaneDataSharingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
