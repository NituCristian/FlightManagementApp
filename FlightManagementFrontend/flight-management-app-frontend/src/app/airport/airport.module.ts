import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { AirportRoutingModule } from './airport-routing.module';
import { AirportListComponent } from './airport-list/airport-list.component';
import { SharedModule } from '../shared/shared.module';
import { AirportRootComponent } from './airport-root/airport-root.component';
import { AirportCreateComponent } from './airport-create/airport-create.component';
import { AirportInfoComponent } from './airport-info/airport-info.component';
import { AirportFormComponent } from './airport-form/airport-form.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {RequestInterceptorService} from '../request-interceptor.service';
import { LoggingInterceptorService } from '../logging-interceptor.service';
import { ErrorInterceptor } from '../error.interceptor';
import { NotifierModule } from 'angular-notifier';
import { NotificationConfig } from '../shared/notification-config';

@NgModule({
  declarations: [AirportListComponent, AirportRootComponent, AirportCreateComponent, AirportInfoComponent, AirportFormComponent],
  imports: [
    NotifierModule.withConfig(NotificationConfig.customNotifierOptions),
    CommonModule,
    AirportRoutingModule,
    HttpClientModule,
    FormsModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptorService, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptorService, multi: true}
  ]
})
export class AirportModule { }
