import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyRootComponent } from './company-root.component';

describe('CompanyRootComponent', () => {
  let component: CompanyRootComponent;
  let fixture: ComponentFixture<CompanyRootComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompanyRootComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyRootComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
