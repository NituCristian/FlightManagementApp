import {
	HttpEvent,
	HttpHandler,
	HttpRequest,
	HttpErrorResponse,
	HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Injectable } from "@angular/core";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
	constructor(private router: Router) { }
	intercept(
		request: HttpRequest<any>,
		next: HttpHandler
	): Observable<HttpEvent<any>> {
		return next.handle(request)
			.pipe(
				retry(0),
				catchError((error: HttpErrorResponse) => {
					if (error.error.status===403 || error.error.status===401) {
						this.router.navigate(['error']);
						return;
					} 
					return throwError(error);
				})
			)
	}
}