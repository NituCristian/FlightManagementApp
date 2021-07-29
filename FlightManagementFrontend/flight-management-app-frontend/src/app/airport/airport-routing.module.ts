import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from '../authorization/guard/authguard.service';
import { AirportInfoComponent } from './airport-info/airport-info.component';
import { AirportRootComponent } from './airport-root/airport-root.component';
import { AirportGuardService } from '../authorization/guard/airport-guard.service';

const routes: Routes = [
  {
    path:'airport',
    component: AirportRootComponent,
    canActivate:  [AuthGuardService]
  },
  {
    path:'airport/:id',
    component: AirportInfoComponent,
    canActivate:  [AuthGuardService, AirportGuardService]
  },
 
];
   
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AirportRoutingModule { }
