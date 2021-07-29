import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompanyDataSharingService {

  public companyName= new BehaviorSubject<String>("");

  companyNameObservable = this.companyName.asObservable();

  sendCompanyName (name: String): void {
    this.companyName.next(name);
  }

  constructor() { }
}
