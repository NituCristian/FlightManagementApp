import { Injectable } from '@angular/core';
import { BackendService } from '../backend/backend.service';
import { UserCredentials } from './usercredentials';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../authorization/auth.service';
import { Router } from '@angular/router';
import { UserDetailsService } from '../user/user-details.service';
import {JwtTokenService} from '../authorization/jwt-token.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginUrl: string ="http://localhost:8081/authenticate";
  constructor(  private http: HttpClient,
                private authService: AuthService,
                private router: Router,
                private  userDetailsService: UserDetailsService, 
                private jwtTokenService: JwtTokenService) { }

  authenticate(userCredentials: UserCredentials): void {
       this.http.post(this.loginUrl, userCredentials, {​​​​ observe: 'response' }).subscribe(resp=>{

          this.jwtTokenService.setToken(resp.headers.get("Authorization"));
          if(this.authService.canAuthenticateUser()){
            this.setActiveUserClaims();
            this.router.navigate(['']);
          }
      }, err=>{
        alert(err.error);
        })

  }

  setActiveUserClaims(){
    this.userDetailsService.setActiveUserClaims(this.userDetailsService.getUserClaimsFromJWT());
  }

}
