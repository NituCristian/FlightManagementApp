import { Component,  OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { Airport } from '../airport';
import { AirportService } from '../airport.service';

@Component({
  selector: 'app-airport-info',
  templateUrl: './airport-info.component.html',
  styleUrls: ['./airport-info.component.css']
})
export class AirportInfoComponent implements OnInit {

  airportId: number

  airportToEdit: Airport;
  
  errorMessage: String;

  constructor(private router: Router, private route: ActivatedRoute, 
    private airportService: AirportService,
    private notifierService: NotifierService) { 

    }

  ngOnInit(): void {

  }

  editAirport(event: Airport) {
    this.airportToEdit = event

    this.airportService.editAirport(this.airportToEdit).subscribe(() => {

      this.airportService.getAirportById(+this.route.snapshot.paramMap.get('id')).subscribe((airport) => {
        this.airportToEdit = airport;
        this.router.navigate(['/airport']);
      });

    }, (error1) => {
       this.notifierService.notify("error", error1.error);
    });

  }



}
