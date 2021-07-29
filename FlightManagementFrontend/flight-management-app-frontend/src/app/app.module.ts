import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BackendModule } from './backend/backend.module';
import { ErrorPageModule } from './error-page/error-page.module';
import {SharedModule} from './shared/shared.module'
import  {LoginModule} from './login/login.module';
import {MainViewModule} from './main-view/main-view.module';
import { AirportModule } from './airport/airport.module';
import {FlightTemplateModule} from './flight-template/flight-template.module'
import {FormsModule} from '@angular/forms';
import  {PlaneModule} from './plane/plane.module';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';
import { LoggingInterceptorService } from 'src/app/logging-interceptor.service';
import {RoutingPageNotFoundModule} from './shared/routing-page-not-found/routing-page-not-found.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BackendModule,
    ErrorPageModule,
    LoginModule,
    MainViewModule,
    PlaneModule,
    AirportModule,
    FlightTemplateModule,
    SharedModule,
    RoutingPageNotFoundModule,
    LoggerModule.forRoot({
      level: NgxLoggerLevel.TRACE,
      serverLogLevel: NgxLoggerLevel.ERROR,
      disableConsoleLogging: true
    })
  ],
  providers: [LoggingInterceptorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
