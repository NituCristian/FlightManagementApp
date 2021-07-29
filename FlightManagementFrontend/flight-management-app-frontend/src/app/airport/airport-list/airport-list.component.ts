import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Airport } from '../airport';
import { AirportService } from '../airport.service';
import { ConfirmationDialogService } from '../../shared/confirmation-dialog.service';
import { UserClaims } from 'src/app/user/claims/user-claims';
import { UserDetailsService } from 'src/app/user/user-details.service';
import { RoleManagementService } from 'src/app/shared/role-management.service';

@Component({
  selector: 'app-airport-list',
  templateUrl: './airport-list.component.html',
  styleUrls: ['./airport-list.component.css']
})
export class AirportListComponent implements OnInit {

  airportList: Airport[];

  @Output()
  view = new EventEmitter();

  @Output()
  airportEmitter = new EventEmitter();

  airportId: number

  private activeUserClaims: UserClaims;

  constructor(
    private route: Router,
    private airportService: AirportService,
    private confirmationDialogService: ConfirmationDialogService,
    private userDetailsService: UserDetailsService,
    private roleManagementService: RoleManagementService
  ) {
    this.userDetailsService.getActiveUserClaims().subscribe(userClaims => this.activeUserClaims = userClaims).unsubscribe();
  }

  ngOnInit(): void {
    this.getAirports()
  }

  getAirports() {
    this.airportService.getAllAirports().subscribe((airportList) => {
      this.airportList = airportList;
    })
  }

  goToCreatePage() {
    this.view.emit(2)
  }

  goToEditPage() {
    this.route.navigate(["airport" + "/" + this.airportId]);
  }

  deleteAirport() {
    this.airportService.deleteAirportById(this.airportId).subscribe(() => {

      this.getAirports()
      
    }, () => {
      alert('Airport cannot be deleted. Please enter valid data.');
    });
  }

  openConfirmationDialog() {
    this.confirmationDialogService
      .confirm("Please confirm..", "Do you really want to delete the airport ?")
      .then((confirmed) => {
        if (confirmed == true) {
          this.deleteAirport()
        }

      })
  }

  isAdmin(): boolean {
    return this.roleManagementService.isAdmin(this.activeUserClaims);
  }

  isCompanyManager(): boolean {
    return this.roleManagementService.isCompanyManager(this.activeUserClaims);
  }

}
