import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlightTemplateRoutingModule } from './flight-template-routing.module';
import { FlightTemplateListComponent } from './flight-template-list/flight-template-list.component';
import { SharedModule } from '../shared/shared.module';
import { FlightTemplateInfoComponent } from './flight-template-info/flight-template-info.component';
import { FlightTemplateFormComponent } from './flight-template-form/flight-template-form.component';
import { FlightTemplateCreateComponent } from './flight-template-create/flight-template-create.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { RequestInterceptorService } from '../request-interceptor.service';
import { ErrorInterceptor } from '../error.interceptor';
import { LoggingInterceptorService } from '../logging-interceptor.service';
import { NotifierModule } from "angular-notifier";
import { NotificationConfig } from '../shared/notification-config';

@NgModule({
  declarations: [FlightTemplateListComponent, FlightTemplateInfoComponent, FlightTemplateFormComponent, FlightTemplateCreateComponent],
  imports: [
    NotifierModule.withConfig(NotificationConfig.customNotifierOptions),
    CommonModule,
    FlightTemplateRoutingModule,
    HttpClientModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptorService, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptorService, multi: true }
  ]
})
export class FlightTemplateModule { }
