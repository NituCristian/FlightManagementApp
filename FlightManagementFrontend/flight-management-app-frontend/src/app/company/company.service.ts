import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BackendService } from '../backend/backend.service';
import { Plane } from '../plane/plane';
import { Company } from './company';
@Injectable({
  providedIn: 'root'
})
export class CompanyService implements OnInit {

  private companyEndpoint = 'http://localhost:8081/companies';
  private getPlanesByCompanyURL = "http://localhost:8081/companies/planes"

  companies: Company[] = [];

  constructor(private backednService: BackendService) { }


  ngOnInit(): void {

  }

  getCompanyById(id: Number): Observable<Company>{
    return this.backednService.get(this.companyEndpoint+ "/"+id);

  }

  getCompanies(): Observable<Company[]> {
    return this.backednService.get(this.companyEndpoint);
  }

  update(company: Company): Observable<Company> {
    return this.backednService.put(this.companyEndpoint, company);
  }

  save(company: Company): Observable<Company> {
    return this.backednService.post(this.companyEndpoint, company);
  }

  delete(company: Company): Observable<Company> {
    return this.backednService.deleteWithBody(this.companyEndpoint, company);
  }

  getPlanesByCompany(company: Company): Observable<Plane[]> {
    return this.backednService.post(this.getPlanesByCompanyURL, company);
  }

  getCompanyByName(name: string): Observable<Company[]> {
    return this.backednService.get(this.companyEndpoint).pipe(map(companies => companies.filter(company => company.name === name)));
  }
}
