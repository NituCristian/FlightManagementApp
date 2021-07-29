import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirportRootComponent } from './airport-root.component';

describe('AirportRootComponent', () => {
  let component: AirportRootComponent;
  let fixture: ComponentFixture<AirportRootComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirportRootComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirportRootComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
