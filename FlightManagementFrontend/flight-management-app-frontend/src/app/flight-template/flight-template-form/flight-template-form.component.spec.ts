import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightTemplateFormComponent } from './flight-template-form.component';

describe('FlightTemplateFormComponent', () => {
  let component: FlightTemplateFormComponent;
  let fixture: ComponentFixture<FlightTemplateFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightTemplateFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightTemplateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
