import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Company } from '../company';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CompanyService } from '../company.service';
import { CompanyDataSharingService } from '../company-data-sharing.service';
import { NotifierService } from "angular-notifier";


@Component({
  selector: 'app-company-info',
  templateUrl: './company-info.component.html',
  styleUrls: ['./company-info.component.css']
})
export class CompanyInfoComponent implements OnInit {

  private readonly notifier: NotifierService;
  companyInfoForm: FormGroup;
  areFormsDisabled: boolean = false;
  company: Company;
  companyName: String = '';
  companyAddress: String = '';
  companyEmail: String = '';
  companyPhone: String = '';

  constructor(public activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private companyService: CompanyService,
    private router: Router,
    private companyDataSharingService: CompanyDataSharingService,
    private notifierService: NotifierService) {

    this.notifier = notifierService;
    this.companyDataSharingService.companyNameObservable.subscribe(companyName => {
      if (companyName !== "") {
        this.companyName = companyName;
      }else{
        let companyNameFromUrl=this.router.url.split('/')[2]
        if(companyNameFromUrl!=='%23'){
          this.companyName = companyNameFromUrl;
        }

      }
    }).unsubscribe();
    this.companyDataSharingService.sendCompanyName("");
  }

  ngOnInit(): void {
    this.initializeInfoForm();

    if (this.companyName !== '') {
      this.companyService.getCompanyByName(this.companyName.valueOf()).subscribe(companies => {
        this.company = companies[0];
        if (this.company) {
          this.companyName = this.company.name;
          this.companyAddress = this.company.address;
          this.companyEmail = this.company.email;
          this.companyPhone = this.company.phone;
        }
        this.initializeInfoForm();
      });
    }
  }

  initializeInfoForm(): void {
    this.companyInfoForm = this.formBuilder.group({
      name: new FormControl({ value: this.companyName, disabled: this.areFormsDisabled }, [Validators.required]),
      address: new FormControl({ value: this.companyAddress, disabled: this.areFormsDisabled }, [Validators.required]),
      email: new FormControl({ value: this.companyEmail, disabled: this.areFormsDisabled }, [Validators.required, Validators.pattern("[a-z][a-zA-Z1-9_]*@[a-zA-Z1-9_]*(\\.[a-z]{2,4})+")]),
      phone: new FormControl({ value: this.companyPhone, disabled: this.areFormsDisabled }, [Validators.required, Validators.pattern("\\+[0-9]{2}[0-9]{9}")])
    })
  }

  save(): void {
    if (this.formIsValid()) {

      if (!this.isInsertOperation()) {
        this.performUpdateOperation();
        this.company = null;
      } else {
        this.performInsertOperation();
        this.company = null;
      }

    }
  }

  formIsValid(): boolean {
    if (this.companyInfoForm.valid) {
      return true;
    } else {
      this.companyInfoForm.markAsDirty();
      this.companyInfoForm.markAllAsTouched();
      return false;
    }
  }


  getEditedCompany(): Company {
    const company: Company = {

      id: this.getCompanyId(),
      name: this.companyInfoForm.get("name").value,
      address: this.companyInfoForm.get("address").value,
      email: this.companyInfoForm.get("email").value,
      phone: this.companyInfoForm.get("phone").value
    }
    return company;
  }


  getCompanyId(): Number {
    if (!this.company) {
      return null;
    }
    return this.company.id;
  }

  isInsertOperation(): boolean {
    if (!this.company) {
      return true;
    }
    return false;
  }

  performUpdateOperation(): void {

    const company = this.getEditedCompany();

    this.companyService.update(company).subscribe(company => {
      this.notifier.notify("success", "The Company Information was successfully updated");

      setTimeout(() => { this.router.navigate(['companies']) }, 3000)
    },
      err => {
        this.notifier.notify("error", err.error);
      }
    );
  }

  performInsertOperation(): void {
    const company = this.getEditedCompany();
    this.companyService.save(company).subscribe(company => {
      this.notifier.notify("success", "The Company Information was successfully saved");
      setTimeout(() => {
        this.emptyCompanyInfoForm();
        this.router.navigate(['companies'])
      }, 3000)
    },
      err => {
        this.notifier.notify("error", err.error);
      }
    );
  }

  clearForm(): void {
    this.initializeInfoForm();
  }

  close() {
    this.company = null;
    this.companyName = null;
    this.companyAddress = null;
    this.companyEmail = null;
    this.companyPhone = null;
    this.companyDataSharingService.sendCompanyName("");
    this.router.navigate(['companies']);

  }

  phoneNumberNotInputed(): boolean {
    return this.companyInfoForm.controls.phone.touched &&
      this.companyInfoForm.get('phone').value === '';
  }

  phoneNumberFormatNotRespected(): boolean {
    return this.companyInfoForm.controls.phone.touched &&
      (this.companyInfoForm.controls.phone.invalid
        && this.companyInfoForm.get('phone').value !== '');
  }

  emailFormatNotRespected(): boolean {
    return this.companyInfoForm.controls.email.touched &&
      (this.companyInfoForm.controls.email.invalid
        && this.companyInfoForm.get('email').value !== '');
  }

  emailNotInputed(): boolean {
    return this.companyInfoForm.controls.email.touched &&
      this.companyInfoForm.get('email').value === '';
  }

  emptyCompanyInfoForm(): void {

    this.company = null;
    this.companyName = null;
    this.companyAddress = null;
    this.companyEmail = null;
    this.companyPhone = null;
    this.companyDataSharingService.sendCompanyName("");

  }

}
