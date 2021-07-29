import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SharedModule} from '../shared/shared.module';
import { MainViewRoutingModule } from './main-view-routing.module';
import { MainViewComponent } from './main-view/main-view.component';
import {CompanyModule} from '../company/company.module';
import { CompanyRoutingModule } from '../company/company-routing.module';

@NgModule({
  declarations: [MainViewComponent],
  imports: [
    CommonModule,
    MainViewRoutingModule,
    SharedModule,
    CompanyModule,
    CompanyRoutingModule
  ],
  exports: [MainViewComponent]
})
export class MainViewModule { }
