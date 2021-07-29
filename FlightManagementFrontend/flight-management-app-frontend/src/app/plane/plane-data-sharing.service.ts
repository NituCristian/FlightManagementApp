import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlaneDataSharingService {

  constructor() { }

  public planeId = new BehaviorSubject<Number>(0);
  planeIdObservable = this.planeId.asObservable();

  public createNewPlane= new BehaviorSubject<boolean>(false);
  createNewPlaneObservable = this.createNewPlane.asObservable();

  sendPlaneId (id: Number): void {
    this.planeId.next(id);
  }

  setCreateNewPlane(val : boolean): void {
    this.createNewPlane.next(val);
  }

}
