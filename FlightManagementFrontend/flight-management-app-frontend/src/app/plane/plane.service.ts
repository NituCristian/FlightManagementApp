import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BackendService } from '../backend/backend.service';
import { Plane } from './plane';

@Injectable({
  providedIn: 'root'
})
export class PlaneService {

  private planesURL= 'http://localhost:8081/planes';
  private planesByCompanyURL= 'http://localhost:8081/planes/company';

  constructor(private backednService: BackendService) { }


  getById(id: Number): Observable<Plane>{
      return this.backednService.get(this.planesURL+"/"+id);
  }

  update(plane: Plane) : Observable<Plane> {
    return this.backednService.put(this.planesURL, plane);
  }

  getAll() : Observable<Plane[]>  {
    return this.backednService.get(this.planesURL);
  }

  getAllByCompanyId(companyId:Number): Observable<Plane[]> {
    return this.backednService.post(this.planesByCompanyURL, companyId);

  }

  delete(plane: Plane): Observable<Plane> {
    return this.backednService.deleteWithBody(this.planesURL, plane);
  }

  save(plane: Plane): Observable<Plane>{
    return this.backednService.post(this.planesURL, plane);
  }

}
