import { Injectable } from '@angular/core';
import { BackendService } from 'src/app/backend/backend.service';
import { Router } from '@angular/router';
import { Observable, BehaviorSubject} from 'rxjs';

import { Airport, IAirport } from './airport';

@Injectable({
  providedIn: 'root'
})
export class AirportService {

  private endpoint = 'http://localhost:8081/airports/';
  
  constructor(private backendService: BackendService, private router: Router) {
 
  }
 
  getAllAirports(): Observable<any> {
    return this.backendService.get(this.endpoint);
  }

  getAirportById(id: number): Observable<any> {
    return this.backendService.get(this.endpoint + id);
  }

  addAirport(airport: IAirport): Observable<any> {
    return this.backendService.post(this.endpoint, airport);
  }

  editAirport(airport: Airport): Observable<any> {
    return this.backendService.put(this.endpoint, airport);
  }

  deleteAirportById(id: number): Observable<any> {
    return this.backendService.delete(this.endpoint + id);
  }
 
}