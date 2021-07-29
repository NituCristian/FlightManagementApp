import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/authorization/auth.service';
import { UserClaims } from 'src/app/user/claims/user-claims';
import { UserDetailsService } from 'src/app/user/user-details.service';
import { RoleManagementService } from 'src/app/shared/role-management.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  private activeUserClaims: UserClaims;
  pathName: String;

  constructor(private authService: AuthService, private userDetailsService: UserDetailsService,
    private roleManagementService: RoleManagementService, private route: ActivatedRoute) {
   
      this.userDetailsService.getActiveUserClaims().subscribe(userClaims=> this.activeUserClaims=userClaims).unsubscribe();
   
      route.url.subscribe(() => {
        this.pathName = window.location.pathname;
       });
    }

  ngOnInit(): void {

  }

  logout():void {
    this.authService.logout();
  }

  isAdmin(): boolean{
    return this.roleManagementService.isAdmin(this.activeUserClaims);
  }

  isCompanyManager(): boolean {
    return this.roleManagementService.isCompanyManager(this.activeUserClaims);
  }

  isFlightManagerOrCrewMember(): boolean {
    return this.roleManagementService.isFlightManagerOrCrewMember(this.activeUserClaims);
  }

  isFlightManager(): boolean {
    return this.roleManagementService.isFlightManager(this.activeUserClaims);
  }

}




