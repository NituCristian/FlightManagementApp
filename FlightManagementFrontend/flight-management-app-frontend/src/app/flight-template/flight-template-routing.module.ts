import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from '../authorization/guard/authguard.service';
import { FlightTemplateListComponent } from './flight-template-list/flight-template-list.component';
import {FlightTemplateInfoComponent} from './flight-template-info/flight-template-info.component';
import {FlightTemplateCreateComponent} from './flight-template-create/flight-template-create.component';
import {FlightTemplateRoleGuardService} from '../authorization/guard/flight-template-role-guard.service';

const routes: Routes = [
  {
    path:'flight-template',
    component: FlightTemplateListComponent,
    canActivate:  [AuthGuardService, FlightTemplateRoleGuardService]
  }, 
  {
    path: 'flight-template/:id',
    component: FlightTemplateInfoComponent,
    canActivate:  [AuthGuardService, FlightTemplateRoleGuardService]
  },
  {
    path: 'create-flight-template',
    component: FlightTemplateCreateComponent,
    canActivate:  [AuthGuardService, FlightTemplateRoleGuardService]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FlightTemplateRoutingModule { }
