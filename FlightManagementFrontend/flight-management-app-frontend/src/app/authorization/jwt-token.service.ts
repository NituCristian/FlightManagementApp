import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtTokenService {

  jwtToken: BehaviorSubject<string> = new BehaviorSubject<string>(sessionStorage.getItem("token"));

  constructor() { }

  setToken(token: string): void {
    this.jwtToken.next(token);
    sessionStorage.setItem("token", token)
  }

  getToken(): string {
    return this.jwtToken.getValue();
  }

  isTokenExpired(): boolean {
    let jwt = this.getToken();

    if (!jwt) {
      return true;
    }
    const { exp } = jwt_decode(jwt);

    return exp * 1000 < Date.now() + 1;
  }
}
