import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightTemplateCreateComponent } from './flight-template-create.component';

describe('FlightTemplateCreateComponent', () => {
  let component: FlightTemplateCreateComponent;
  let fixture: ComponentFixture<FlightTemplateCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightTemplateCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightTemplateCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
