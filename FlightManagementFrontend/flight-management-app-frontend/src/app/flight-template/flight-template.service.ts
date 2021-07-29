import { Injectable } from '@angular/core';
import { BackendService } from 'src/app/backend/backend.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlightTemplateService {

  private endpoint = 'http://localhost:8081/flight_templates';

  constructor(private backendService: BackendService) { }

  getAllFlightTemplates(): Observable<any> {
    return this.backendService.get(this.endpoint);
  }

  getFlightTemplateById(id): Observable<any> {
    const getByIdEndpoint = this.endpoint + "/" + id;
    return this.backendService.get(getByIdEndpoint);
  }

  insertFlightTemplate(newFlightTemplate): Observable<any> {
    return this.backendService.post(this.endpoint, JSON.stringify(newFlightTemplate));
  }

  updateFlightTemplate(updatedFlightTemplate): Observable<any> {
    return this.backendService.put(this.endpoint, updatedFlightTemplate);
  }

  deleteFlightTemplate(id): Observable<any> {
    const deleteEndpoint = this.endpoint + "/" + id;
    return this.backendService.delete(deleteEndpoint);
  }
}
