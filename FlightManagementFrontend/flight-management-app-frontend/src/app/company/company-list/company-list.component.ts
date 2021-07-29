import { Component, OnInit } from '@angular/core';
import { CompanyService } from '../company.service';
import { Company } from '../company';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationDialogService } from 'src/app/shared/confirmation-dialog.service';
import { CompanyDataSharingService } from '../company-data-sharing.service';
import { UserDetailsService } from 'src/app/user/user-details.service';
import { UserClaims } from '../../user/claims/user-claims';
@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.css']
})
export class CompanyListComponent implements OnInit {

  constructor(private companyService: CompanyService,
    private router: Router, private route: ActivatedRoute,
    private confirmationDialogService: ConfirmationDialogService,
    private companyDataSharingService: CompanyDataSharingService,
    private userDetailsService: UserDetailsService) { }

  companies: Company[]

  ngOnInit(): void {
    this.getCompanies();
  }

  getCompanies(): void {



    this.companyService.getCompanies().subscribe(companies => {
      this.companies = companies;

    });


  }

  seeCompanyInfoOrEdit(company: Company) {
    this.companyDataSharingService.sendCompanyName(company.name);
    this.router.navigate([company.name], { relativeTo: this.route });
  }

  deleteCompany(company: Company) {

    this.companyService.delete(company).subscribe(() => {
      this.updateCompanyList(company);
    })
  }
  openConfirmationDialog(company: Company) {
    this.confirmationDialogService
      .confirm("Please confirm..", "Do you really want to delete the company ?")
      .then((confirmed) => {
        if (confirmed == true) {
          this.deleteCompany(company);
        }
      });
  }


  updateCompanyList(company: Company) {
    this.companies = this.companies.filter(c => c.id !== company.id);
  }

  isActiveUsersCompany(companyId: Number): boolean {

    let activeUserClaimes: UserClaims;
    this.userDetailsService.getActiveUserClaims().subscribe(userClaims => activeUserClaimes = userClaims).unsubscribe();
    return companyId === activeUserClaimes.companyId;
  }


}
