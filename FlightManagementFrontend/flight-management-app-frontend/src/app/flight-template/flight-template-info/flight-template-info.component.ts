import { Component, OnInit } from '@angular/core';
import { FlightTemplate } from '../flight-template';
import { FlightTemplateService } from '../flight-template.service';
import { Router } from '@angular/router';
import { NotifierService } from "angular-notifier";

@Component({
  selector: 'app-flight-template-info',
  templateUrl: './flight-template-info.component.html',
  styleUrls: ['./flight-template-info.component.css']
})
export class FlightTemplateInfoComponent implements OnInit {

  flightTemplateId: number;
  flightTemplate: FlightTemplate;

  private readonly notifier: NotifierService;

  constructor(private flightTemplateService: FlightTemplateService, private route: Router, private notifierService: NotifierService) {
    this.notifier = notifierService;
  }

  ngOnInit(): void {

  }

  updateFlightTemplate(editedAirport) {
    this.flightTemplateService.updateFlightTemplate(editedAirport).subscribe((flightTemplate) => {
      this.route.navigate(["flight-template"]);
    },
      (error) => {
        this.notifier.notify("error", error);
      });
  }


}
