import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaneInfoSharedComponent } from './plane-info-shared.component';

describe('PlaneInfoSharedComponent', () => {
  let component: PlaneInfoSharedComponent;
  let fixture: ComponentFixture<PlaneInfoSharedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaneInfoSharedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaneInfoSharedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
