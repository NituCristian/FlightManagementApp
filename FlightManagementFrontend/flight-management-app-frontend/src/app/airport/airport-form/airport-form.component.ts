import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators , ReactiveFormsModule} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Airport, IAirport } from '../airport';
import { AirportService } from '../airport.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-airport-form',
  templateUrl: './airport-form.component.html',
  styleUrls: ['./airport-form.component.css']
})
export class AirportFormComponent implements OnInit {

  airportInfoForm: FormGroup;

  areFormsDisabled: false;

  updateSuccessMessage:String;

  insertSuccessMessage: String;

  errorMessage: String;

  toEdit: boolean = false;

  airportForErrors: Airport;

  constructor(private fb: FormBuilder,
      private route: ActivatedRoute,
      private airportService: AirportService,
      private router: Router,
      private _location: Location
  ) { }

  airport: IAirport;

  airportToEdit: Airport;

  existentAirport: Airport;

  id: number;

  @Output()
  airportEmitter = new EventEmitter()

  ngOnInit(): void {
    this.initializeForm();
    
  }

  initializeForm(){
    this.airportInfoForm = this.fb.group({
      airportCode: new FormControl({value: '', disabled: this.areFormsDisabled}, [Validators.required]),
      address: new FormControl({value: '', disabled: this.areFormsDisabled}, [Validators.required]),
      city: new FormControl({value: '', disabled: this.areFormsDisabled}, [Validators.required]),
      name: new FormControl({value: '', disabled: this.areFormsDisabled}, [Validators.required]),
    })

    if(+this.route.snapshot.paramMap.get('id')== 0) {
      
      this.toEdit = false;

      this.airportInfoForm.setValue(
        {
          airportCode: "",
          address: "",
          city: "",
          name: ""
        }
      )
    }

    else {
      this.toEdit = true;

      this.airportService.getAirportById(+this.route.snapshot.paramMap.get('id')).subscribe((airport) => {
        this.airportToEdit = airport;
        this.existentAirport = airport;

        this.airportInfoForm.setValue(
          {
            airportCode: this.airportToEdit.airportCode,
            address: this.airportToEdit.address,
            city: this.airportToEdit.city,
            name: this.airportToEdit.name
          }
        )
      });
    }
  }

  onSubmit(){

    if(+this.route.snapshot.paramMap.get('id') == 0) {
      
      if (!this.airport) {
        this.airport = {
          airportCode: '', address: '', city: '', name: ''
        };
      }

      this.airport.airportCode = this.airportInfoForm.controls.airportCode.value
      this.airport.address = this.airportInfoForm.controls.address.value
      this.airport.name = this.airportInfoForm.controls.name.value
      this.airport.city = this.airportInfoForm.controls.city.value        
    
      this.airportEmitter.emit(this.airport)        
    }

    else {

      if (!this.airportToEdit) {
        this.airportToEdit = {
          id: 0, airportCode: '', address: '', city: '', name: ''
        };
      }

      this.airportToEdit.id = +this.route.snapshot.paramMap.get('id')
      this.airportToEdit.airportCode = this.airportInfoForm.controls.airportCode.value
      this.airportToEdit.address = this.airportInfoForm.controls.address.value
      this.airportToEdit.name = this.airportInfoForm.controls.name.value
      this.airportToEdit.city = this.airportInfoForm.controls.city.value

      this.airportEmitter.emit(this.airportToEdit)
    }
 
  } 

  cancelData() {

    if(this.toEdit == true) {
      this.router.navigate(['airport']);
    }

    else {
      this._location.back();
    }
  }

  
}
