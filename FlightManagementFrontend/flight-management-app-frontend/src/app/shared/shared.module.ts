import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { NavbarComponent } from './navbar/navbar.component';
import { ConfirmationDialogService } from './confirmation-dialog.service';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';


@NgModule({
  declarations: [NavbarComponent, ConfirmationDialogComponent],
  imports: [
    CommonModule,
    SharedRoutingModule
  ],
  exports:[NavbarComponent, ConfirmationDialogComponent],
  
  providers: [ConfirmationDialogService]
})
export class SharedModule { }
