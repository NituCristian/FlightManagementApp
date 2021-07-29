import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightTemplateInfoComponent } from './flight-template-info.component';

describe('FlightTemplateInfoComponent', () => {
  let component: FlightTemplateInfoComponent;
  let fixture: ComponentFixture<FlightTemplateInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightTemplateInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightTemplateInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
