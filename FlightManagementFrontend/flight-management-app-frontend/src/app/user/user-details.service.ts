import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserClaims } from './claims/user-claims';
import  jwt_decode from 'jwt-decode';
import * as CryptoJS from 'crypto-js'; 
import { JwtTokenService } from '../authorization/jwt-token.service';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  activeUser= new BehaviorSubject<UserClaims>(null);

  secretKey: string ="hxdy$mHDDGG1683U";
  constructor(private jwtTokenService: JwtTokenService) { }

  setActiveUserClaims(claims: UserClaims){
    this.activeUser.next(claims);
  }

  getActiveUserClaims(): Observable<UserClaims>{
    if(this.activeUser.value===null){
      
      this.activeUser=new BehaviorSubject<UserClaims>(this.getUserClaimsFromJWT());
    }
      return this.activeUser.asObservable();
  }

  getUserClaimsFromJWT(): UserClaims {
    let jwt=this.jwtTokenService.getToken();
    const {claimDetails} = jwt_decode(jwt);

    let encryptedBase64Key = btoa(this.secretKey)

    var parsedBase64Key = CryptoJS.enc.Base64.parse(encryptedBase64Key);

    let decryptedData = CryptoJS.AES.decrypt( claimDetails, parsedBase64Key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    });

    var decryptedText = decryptedData.toString( CryptoJS.enc.Utf8 );

    let userClaims: UserClaims = JSON.parse(decryptedText)
   
    return userClaims;

  }
}
