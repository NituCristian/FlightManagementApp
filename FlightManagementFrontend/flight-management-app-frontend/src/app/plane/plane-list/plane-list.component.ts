import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationDialogService } from 'src/app/shared/confirmation-dialog.service';
import { RoleManagementService } from 'src/app/shared/role-management.service';
import { UserClaims } from 'src/app/user/claims/user-claims';
import { UserDetailsService } from 'src/app/user/user-details.service';
import { Plane } from '../plane';
import { PlaneService } from '../plane.service';

@Component({
  selector: 'app-plane-list',
  templateUrl: './plane-list.component.html',
  styleUrls: ['./plane-list.component.css']
})
export class PlaneListComponent implements OnInit {

  planes: Plane[] = [];
  userClaims: UserClaims;
  constructor(private planeService: PlaneService, private route: ActivatedRoute, private router: Router, private confirmationDialogService: ConfirmationDialogService, private userDetailsService: UserDetailsService,
    public roleManagementService: RoleManagementService) {

    this.userDetailsService.getActiveUserClaims().subscribe(userClaims => this.userClaims = userClaims).unsubscribe()

  }

  ngOnInit(): void {
    if (this.roleManagementService.isAdmin(this.userClaims)) {
      this.getAllPlanes();

    } else {
      this.getAllPlanesByCompany();
    }
  }


  getAllPlanes(): void {

    this.planeService.getAll().subscribe(planes => this.planes = planes);


  }
  getAllPlanesByCompany(): void {
    this.planeService.getAllByCompanyId(this.userClaims.companyId).subscribe(planes => this.planes = planes);
  }

  seePlaneInfoOrEdit(plane: Plane): void {
    this.router.navigate([plane.id], { relativeTo: this.route });
  }


  openConfirmationDialog(plane: Plane) {
    this.confirmationDialogService
      .confirm("Please confirm..", "Do you really want to delete the plane ?")
      .then((confirmed) => {
        if (confirmed == true) {
          this.delete(plane);
        }
      });
  }

  delete(plane: Plane) {
    this.planeService.delete(plane).subscribe(response => this.updatePlaneList(plane))
  }

  updatePlaneList(plane: Plane) {
    this.planes = this.planes.filter(p => p.id !== plane.id);
  }

  isAllowedToDelete(): boolean {
    return this.roleManagementService.isAdmin(this.userClaims) || this.roleManagementService.isCompanyManager(this.userClaims);
  }

  isAllowedToEdit(): boolean {
    return this.roleManagementService.isAdmin(this.userClaims) || this.roleManagementService.isCompanyManager(this.userClaims);
  }
}
