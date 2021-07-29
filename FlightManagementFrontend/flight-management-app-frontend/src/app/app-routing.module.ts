import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from './authorization/guard/authguard.service';
import { ErrorPageComponent } from './error-page/error-page/error-page.component';
import { MainViewComponent } from './main-view/main-view/main-view.component';

const routes: Routes=[
 {path:'', component:MainViewComponent,  canActivate:  [AuthGuardService]},
 {path: 'error' , component: ErrorPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
