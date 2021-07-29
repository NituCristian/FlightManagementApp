import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Plane } from '../plane';
import { PlaneDataSharingService } from '../plane-data-sharing.service';
import { PlaneService } from '../plane.service';
import { Company } from 'src/app/company/company';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { RoleManagementService } from 'src/app/shared/role-management.service';
import { UserDetailsService } from 'src/app/user/user-details.service';
import { UserClaims } from 'src/app/user/claims/user-claims';
import { NotifierService } from 'angular-notifier';
import { CompanyService } from 'src/app/company/company.service';
import {Observable, of } from 'rxjs';

@Component({
  selector: 'app-plane-info',
  templateUrl: './plane-info.component.html',
  styleUrls: ['./plane-info.component.css']
})
export class PlaneInfoComponent implements OnInit {

  private readonly notifier: NotifierService;

  meantForCreate: boolean;
  areFormsDisabled: boolean = false;
  planeId: Number;
  planeInfoForm: FormGroup;
  plane: Plane;
  activeUserClaims: UserClaims;

  constructor(private planeDataSharingService: PlaneDataSharingService, private planeService: PlaneService, private formBuilder: FormBuilder, private router: Router,
    private roleManagementService: RoleManagementService, private userDetailsService: UserDetailsService, private notifierService: NotifierService, private companyService: CompanyService) {
    this.planeDataSharingService.planeIdObservable
      .subscribe((id) => {
        this.planeId = id;
      }).unsubscribe();
    this.planeDataSharingService.createNewPlaneObservable
      .subscribe(value => { this.meantForCreate = value }
      ).unsubscribe();
    this.planeDataSharingService.setCreateNewPlane(false);

    this.userDetailsService.getActiveUserClaims().subscribe(userClaims => this.activeUserClaims = userClaims).unsubscribe()
    this.notifier=notifierService;
  }

  ngOnInit(): void {
    if (this.meantForCreate) {
      this.initializeInfoFormForCreate();
      return;
    }
    else if (!this.planeId) {
      this.setPlaneIdFromURL();
      this.getPlaneByIdAndInitializeForm(this.planeId);
    } else {
      this.getPlaneByIdAndInitializeForm(this.planeId);
    }
  }

  getPlaneByIdAndInitializeForm(id: Number): void {
    this.planeService.getById(id).subscribe(plane => {
      this.plane = plane;
      if (this.plane) {
        this.initializeInfoForm();
      }
    });

  }

  initializeInfoForm(): void {

    this.planeInfoForm = this.formBuilder.group({
      name: new FormControl({ value: this.plane.name, disabled: this.areFormsDisabled }, [Validators.required]),
      model: new FormControl({ value: this.plane.model, disabled: this.areFormsDisabled }, [Validators.required]),
      code: new FormControl({ value: this.plane.code, disabled: this.areFormsDisabled }, [Validators.required]),
      numberOfPassengers: new FormControl({ value: this.plane.numberOfPassengers, disabled: this.areFormsDisabled }, [Validators.required, Validators.pattern("([1-4][0-9]{2})|500|([1-9][0-9])")])
    })
  }


  initializeInfoFormForCreate(): void {
    this.planeInfoForm = this.formBuilder.group({
      name: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      model: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      code: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      numberOfPassengers: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required, Validators.pattern("([1-4][0-9]{2})|500|([1-9][0-9])")])
    })
  }

  getEditedPlane(): Promise<Plane> {
    let company= this.getPlaneCompany().toPromise()
    let plane= company.then(comp=>
      
      {

        const plane: Plane = {

          id: this.getPlaneId(),
          name: this.planeInfoForm.get("name").value,
          model: this.planeInfoForm.get("model").value,
          code: this.planeInfoForm.get("code").value,
          numberOfPassengers: Number(this.planeInfoForm.get("numberOfPassengers").value),
          company: comp
        }
    
        return plane;
      }
      )

      return plane;

  }

  save(): void {
    if (this.formIsValid()) {
      if (this.meantForCreate) {
        this.savePlane();

      } else {
        this.updatePlane();

      }
    }

  }

  setPlaneIdFromURL(): void {
    let currentUrl = window.location.href;
    this.planeId = Number(currentUrl.split('/').pop());
  }


  async updatePlane(): Promise<void> {
    let plane = await this.getEditedPlane();
    this.planeService.update(plane).subscribe(plane => {
      this.notifier.notify("success", "The Plane Information was successfully updated")
      setTimeout(() => {
        this.meantForCreate = false;
        this.router.navigate(['planes']);
      }, 5000)
    },
      err => { 
        this.notifier.notify("error", err.error);
        
       }
    )
  }

  async savePlane(): Promise<void> {

    let plane = await this.getEditedPlane();

    this.planeService.save(plane).subscribe(plane => {
      this.notifier.notify("success", "The Plane Information was successfully saved")
      setTimeout(() => {
        this.meantForCreate = false;
        this.router.navigate(['planes']);
      }, 5000)
    },
      err => { 
        this.notifier.notify("error", err.error)
      }
    )

  }

  getPlaneId(): Number {
    if (this.plane) {
      return this.plane.id;
    }
    return null;
  }

  getPlaneCompany(): Observable<Company> {
    if (this.plane) {
      return of(this.plane.company);
    }

    return this.getCompanyById();
  }

  clearForm(): void {
    this.initializeInfoFormForCreate();
  }


  close(): void {

    this.plane = null;
    this.router.navigate(['planes']);

  }


  formIsValid(): boolean {
    if (this.planeInfoForm.valid) {
      return true;
    } else {
      this.planeInfoForm.markAsDirty();
      this.planeInfoForm.markAllAsTouched();
      return false;
    }
  }



  numberOfPassengersNotInputed(): boolean {
    return this.planeInfoForm.controls.numberOfPassengers.touched &&
      this.planeInfoForm.get('numberOfPassengers').value === '';
  }


  numberOfPassengersFormatNotRespected(): boolean {
    return (this.planeInfoForm.controls.numberOfPassengers.touched &&
      (this.planeInfoForm.controls.numberOfPassengers.invalid &&
        this.planeInfoForm.get('numberOfPassengers').value !== '')) ||
      this.planeInfoForm.controls.numberOfPassengers.touched &&
      (this.planeInfoForm.get('numberOfPassengers').value !== '' &&
        (+this.planeInfoForm.get('numberOfPassengers').value > 500 ||
          +this.planeInfoForm.get('numberOfPassengers').value < 10))
  }


  isAllowedToEditOrInsert(): boolean {
    return this.roleManagementService.isCompanyManager(this.activeUserClaims) || this.roleManagementService.isAdmin(this.activeUserClaims);
  }


   getCompanyById(): Observable<Company>{

    let companyId= this.activeUserClaims.companyId;
    return this.companyService.getCompanyById(companyId)
  }


}
