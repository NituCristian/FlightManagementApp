import { Injectable } from '@angular/core';
import { UserClaims } from '../user/claims/user-claims';

@Injectable({
  providedIn: 'root'
})
export class RoleManagementService {
  
  isAdmin(activeUserClaims: UserClaims): boolean{
    if(activeUserClaims.role==="ROLE_ADMIN"){
      return true;
    }
    return false;
  }

  isCompanyManager(activeUserClaims: UserClaims): boolean {
    if(activeUserClaims.role==="ROLE_COMPANY_MANAGER"){
      return true;
    }
    return false;
  }

  isFlightManagerOrCrewMember(activeUserClaims: UserClaims): boolean {
    if(activeUserClaims.role==="ROLE_FLIGHT_MANAGER" || activeUserClaims.role==="ROLE_CREW" ){
      return true;
    }
    return false;
  }

  isFlightManager(activeUserClaims: UserClaims): boolean {
    if(activeUserClaims.role==="ROLE_FLIGHT_MANAGER"){
      return true;
    }
    return false;
  }

  constructor() { }
}
