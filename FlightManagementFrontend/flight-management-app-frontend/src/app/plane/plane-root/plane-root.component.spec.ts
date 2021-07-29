import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaneRootComponent } from './plane-root.component';

describe('PlaneRootComponent', () => {
  let component: PlaneRootComponent;
  let fixture: ComponentFixture<PlaneRootComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaneRootComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaneRootComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
