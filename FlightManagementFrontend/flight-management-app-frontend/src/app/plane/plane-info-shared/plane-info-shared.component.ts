import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Plane } from '../plane';
import {PlaneDataSharingService} from '../plane-data-sharing.service';

@Component({
  selector: 'app-plane-info-shared',
  templateUrl: './plane-info-shared.component.html',
  styleUrls: ['./plane-info-shared.component.css']
})
export class PlaneInfoSharedComponent implements OnInit {

  @Input() plane: Plane;

  constructor(private router: Router, private planeDataSharingService: PlaneDataSharingService) { }

  ngOnInit(): void {
  }

  goToPlaneInfo(): void{
    this.planeDataSharingService.sendPlaneId(this.plane.id);
    this.router.navigate(["planes/"+this.plane.id])
  }
}
