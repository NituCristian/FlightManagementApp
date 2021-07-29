import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from '../authorization/guard/authguard.service';
import { CompanyInfoComponent } from './company-info/company-info.component';
import {CompanyListComponent} from './company-list/company-list.component'
import { CompanyRootComponent } from './company-root/company-root.component';
const routes: Routes = [

  {path:'companies', component: CompanyRootComponent, canActivate: [AuthGuardService],
    children:[
      {path:'', component: CompanyListComponent},
      {path: ':companyName', component: CompanyInfoComponent}
    ]

}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyRoutingModule { }
