import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { RoleManagementService } from 'src/app/shared/role-management.service';
import { UserClaims } from 'src/app/user/claims/user-claims';
import { UserDetailsService } from 'src/app/user/user-details.service';

@Injectable({
  providedIn: 'root'
})
export class FlightTemplateRoleGuardService implements CanActivate {

  private activeUserClaims: UserClaims;

  constructor(private roleService: RoleManagementService, private router: Router, private userDetailsService: UserDetailsService) {
    this.userDetailsService.getActiveUserClaims().subscribe(userClaims => this.activeUserClaims = userClaims).unsubscribe();
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (!(this.roleService.isAdmin(this.activeUserClaims) || this.roleService.isFlightManager(this.activeUserClaims))) {
      this.router.navigate(['error']);
    }
    return true;
  }
}
