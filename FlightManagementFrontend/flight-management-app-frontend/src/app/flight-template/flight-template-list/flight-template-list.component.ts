import { Component, OnInit } from '@angular/core';
import { FlightTemplate } from '../flight-template'
import { FlightTemplateService } from '../flight-template.service';
import { Router } from '@angular/router';
import { ConfirmationDialogService } from 'src/app/shared/confirmation-dialog.service';
@Component({
  selector: 'app-flight-template-list',
  templateUrl: './flight-template-list.component.html',
  styleUrls: ['./flight-template-list.component.css']
})
export class FlightTemplateListComponent implements OnInit {

  flightTemplateList: FlightTemplate[];

  constructor(private flightTemplateService: FlightTemplateService, private route: Router, private confirmationDialogService: ConfirmationDialogService
  ) { }

  ngOnInit(): void {
    this.getAllFlightTemplates();
  }

  goToCreatePage() {
    this.route.navigate(['create-flight-template'])
  }

  getAllFlightTemplates() {
    this.flightTemplateService.getAllFlightTemplates().subscribe((flightTemplateList) => {
      this.flightTemplateList = flightTemplateList;
    })
  }

  updateFlightTemplate(id) {
    this.route.navigate(["flight-template" + "/" + id]);
  }

  deleteFlightTemplate(id) {
    this.flightTemplateService.deleteFlightTemplate(id).subscribe((info) => {
      this.getAllFlightTemplates();
    });
  }

  openConfirmationDialog(id) {
    this.confirmationDialogService
      .confirm("Please confirm..", "Do you really want to delete the flight template ?")
      .then((confirmed) => {
        if (confirmed == true) {
          this.deleteFlightTemplate(id);
        }

      });
  }
}
