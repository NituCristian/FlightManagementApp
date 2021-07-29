import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtTokenService } from './authorization/jwt-token.service';

@Injectable({
  providedIn: 'root'
})
export class RequestInterceptorService implements HttpInterceptor{

  constructor(private jwtTokenService: JwtTokenService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let jwt=this.jwtTokenService.getToken();

    const request = req.clone({ 
      headers: req.headers.set('Authorization', jwt!==null?jwt: ""),
    });
    return next.handle(request);
  }
}