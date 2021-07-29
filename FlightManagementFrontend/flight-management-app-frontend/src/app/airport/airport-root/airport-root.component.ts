import { Component, OnInit } from '@angular/core';
import { Airport } from '../airport';

@Component({
  selector: 'app-airport-root',
  templateUrl: './airport-root.component.html',
  styleUrls: ['./airport-root.component.css']
})
export class AirportRootComponent implements OnInit{

  view: Number = 1;

  airport: Airport
  
  constructor() { }
  
  ngOnInit(): void {
    
  }

  toList(event: Number) {
    this.view = event
  }

  toCreate(event: Number) {
    this.view = event
  }

}
