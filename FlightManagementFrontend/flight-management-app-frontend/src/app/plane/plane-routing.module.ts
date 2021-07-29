import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from '../authorization/guard/authguard.service';
import { PlaneInfoComponent } from './plane-info/plane-info.component';
import { PlaneListComponent } from './plane-list/plane-list.component';
import { PlaneRootComponent } from './plane-root/plane-root.component';
import {PlaneGuardService} from '../authorization/guard/plane-guard.service';
const routes: Routes = [
  {path: "planes", component: PlaneRootComponent, canActivate: [AuthGuardService],
    children: [
      {path: '', component: PlaneListComponent},
      {path: ':id', component: PlaneInfoComponent, canActivate: [PlaneGuardService]}
    ]
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlaneRoutingModule { }
