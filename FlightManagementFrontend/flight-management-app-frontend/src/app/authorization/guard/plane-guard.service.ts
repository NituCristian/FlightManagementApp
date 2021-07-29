import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { RoleManagementService } from 'src/app/shared/role-management.service';
import { UserClaims } from 'src/app/user/claims/user-claims';
import { UserDetailsService } from 'src/app/user/user-details.service';

@Injectable({
  providedIn: 'root'
})
export class PlaneGuardService implements CanActivate {

  constructor(private roleManagementService: RoleManagementService,
    private userDetailsService: UserDetailsService,
    private router: Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    let activeUserClaims: UserClaims;
    this.userDetailsService.getActiveUserClaims().subscribe(userClaims => activeUserClaims = userClaims).unsubscribe();

    if (this.roleManagementService.isAdmin(activeUserClaims) || this.roleManagementService.isCompanyManager(activeUserClaims)) {
      return true;
    }
    this.router.navigate(['error']);

    return false;
  }


}
