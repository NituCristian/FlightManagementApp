import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleManagementService } from 'src/app/shared/role-management.service';
import { UserClaims } from 'src/app/user/claims/user-claims';
import { UserDetailsService } from 'src/app/user/user-details.service';
import { PlaneDataSharingService } from '../plane-data-sharing.service';

@Component({
  selector: 'app-plane-root',
  templateUrl: './plane-root.component.html',
  styleUrls: ['./plane-root.component.css']
})
export class PlaneRootComponent implements OnInit {

  constructor(private planeDataSharingService: PlaneDataSharingService, private router: Router, private route: ActivatedRoute, private userDetailsService: UserDetailsService, private roleManagementService: RoleManagementService) { }

  ngOnInit(): void {
  }

  goToCreatePage(){
    this.planeDataSharingService.setCreateNewPlane(true);
    this.router.navigate(['#'], { relativeTo: this.route })

  }

  isAllowedToCreatePlane(): boolean {
    let activeUserClaims: UserClaims;
    this.userDetailsService.getActiveUserClaims().subscribe(userClaims=> activeUserClaims=userClaims).unsubscribe();

    return this.roleManagementService.isCompanyManager(activeUserClaims) || this.roleManagementService.isAdmin(activeUserClaims);
    
  }




  isOnCreatePage(): boolean{
    let url= window.location.href;
    return url.split("/").pop()==='planes';
  }

}
