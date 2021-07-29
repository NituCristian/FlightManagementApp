import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { __assign } from 'tslib';
import { JwtTokenService } from './jwt-token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router,
    private jwtTokenService: JwtTokenService) { }

  isAuthenticated(): boolean {
    if (!this.jwtTokenService.getToken()) {
      return false;
    }
    else {
      if (!this.canAuthenticateUser()) {
        return false;
      }
    }
    return true;
  }

  canAuthenticateUser(): boolean {
    if (!this.jwtTokenService.isTokenExpired()) {
      return true;
    }
    return false;
  }

  logout(): void {
    this.jwtTokenService.setToken(null);
    this.router.navigate(['login']);
  }

}
