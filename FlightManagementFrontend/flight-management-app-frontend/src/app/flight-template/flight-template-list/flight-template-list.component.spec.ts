import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightTemplateListComponent } from './flight-template-list.component';

describe('FlightTemplateListComponent', () => {
  let component: FlightTemplateListComponent;
  let fixture: ComponentFixture<FlightTemplateListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightTemplateListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightTemplateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
