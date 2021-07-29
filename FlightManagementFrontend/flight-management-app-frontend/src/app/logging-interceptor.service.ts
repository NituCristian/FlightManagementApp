import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class LoggingInterceptorService implements HttpInterceptor {

  constructor(private logger: NGXLogger) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    this.logger.debug(request.url + " " + request.method + " " + new Date());
    this.logger.debug(request.body);

    return next.handle(request);
  }
}
