import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlaneRoutingModule } from './plane-routing.module';
import { PlaneListComponent } from './plane-list/plane-list.component';
import { PlaneInfoComponent } from './plane-info/plane-info.component';
import { PlaneRootComponent } from './plane-root/plane-root.component';
import { PlaneInfoSharedComponent } from './plane-info-shared/plane-info-shared.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { RequestInterceptorService } from '../request-interceptor.service';
import { ErrorInterceptor } from '../error.interceptor';
import { LoggingInterceptorService } from '../logging-interceptor.service';
import { NotifierModule } from 'angular-notifier';
import { NotificationConfig } from '../shared/notification-config';


@NgModule({
  declarations: [PlaneListComponent, PlaneInfoComponent, PlaneRootComponent, PlaneInfoSharedComponent],
  imports: [
    NotifierModule.withConfig(NotificationConfig.customNotifierOptions),
    CommonModule,
    PlaneRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule
  ], 
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptorService, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptorService, multi: true} 
  ],
  exports :[
    PlaneInfoSharedComponent
  ]
})
export class PlaneModule { }
