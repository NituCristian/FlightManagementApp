import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyRoutingModule } from './company-routing.module';
import { CompanyListComponent } from './company-list/company-list.component';
import { CompanyInfoComponent } from './company-info/company-info.component';
import { CompanyRootComponent } from './company-root/company-root.component';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {PlaneModule} from '../plane/plane.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { RequestInterceptorService } from '../request-interceptor.service';
import {ErrorInterceptor} from '../error.interceptor';
import { LoggingInterceptorService } from '../logging-interceptor.service';
import { NotifierModule } from "angular-notifier";
import  {NotificationConfig} from '../shared/notification-config';


@NgModule({
  declarations: [CompanyListComponent, CompanyInfoComponent, CompanyRootComponent],
  imports: [
    NotifierModule.withConfig(NotificationConfig.customNotifierOptions),
    CommonModule,
    ReactiveFormsModule,
    CompanyRoutingModule,
    SharedModule,
    PlaneModule,
    FormsModule
    ],
    providers: [
      { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptorService, multi: true },
      { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
      { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptorService, multi: true}    
    ],
  exports: [CompanyListComponent, CompanyInfoComponent, CompanyRootComponent]
})
export class CompanyModule { }

