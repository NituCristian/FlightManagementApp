import { Component, OnInit } from '@angular/core';
import { FlightTemplate } from '../flight-template';
import { FlightTemplateService } from '../flight-template.service';
import { Router } from '@angular/router';
import { NotifierService } from "angular-notifier";

@Component({
  selector: 'app-flight-template-create',
  templateUrl: './flight-template-create.component.html',
  styleUrls: ['./flight-template-create.component.css']
})
export class FlightTemplateCreateComponent implements OnInit {

  flightTemplate: FlightTemplate;
  name: String;
  arrivalTime: String;
  departureTime: String;
  arrivalAirport: any;
  departureAirport: any;
  plane: any;

  private readonly notifier: NotifierService;

  constructor(private flightTemplateService: FlightTemplateService, private route: Router, private notifierService: NotifierService) {
    this.notifier = notifierService;
  }

  ngOnInit(): void {
  }

  createFlightTemplate(newFlightTemplate) {

    this.flightTemplateService.insertFlightTemplate(newFlightTemplate).subscribe((flightTemplate) => {
      this.route.navigate(["flight-template"]);
    },
      (error) => {
        this.notifier.notify("error", error);
      })
  }
}
