import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { Airport, IAirport } from '../airport';
import { AirportService } from '../airport.service';

@Component({
  selector: 'app-airport-create',
  templateUrl: './airport-create.component.html',
  styleUrls: ['./airport-create.component.css']
})
export class AirportCreateComponent implements OnInit {

  @Output()
  view = new EventEmitter()

  airport: IAirport

  errorMessage: String;

  constructor(private airportService: AirportService, private notifierService: NotifierService) { }

  ngOnInit(): void {

  }

  goBack() {
    this.view.emit(1)
  }

  createAirport(event: Airport) {
    this.airport = event

    this.airportService.addAirport(this.airport).subscribe(() => {

      this.view.emit(1)
    }, (error1) => {
      this.notifierService.notify("error", error1.error);
    });

  }

}
