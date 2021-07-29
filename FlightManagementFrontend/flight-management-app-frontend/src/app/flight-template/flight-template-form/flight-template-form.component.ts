import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { FlightTemplate } from '../flight-template';
import { FlightTemplateService } from '../flight-template.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AirportService } from 'src/app/airport/airport.service';
import { PlaneService } from 'src/app/plane/plane.service';
import { NotifierService } from "angular-notifier";

@Component({
  selector: 'app-flight-template-form',
  templateUrl: './flight-template-form.component.html',
  styleUrls: ['./flight-template-form.component.css']
})
export class FlightTemplateFormComponent implements OnInit {

  flightTemplateForm: FormGroup;
  areFormsDisabled: false;

  flightTemplateId: number;

  airports: any = [];
  planes: any = [];

  flightTemplate: FlightTemplate;
  @Output() flightTemplateEmitter: EventEmitter<FlightTemplate> = new EventEmitter<FlightTemplate>();


  private readonly notifier: NotifierService;

  constructor(private fb: FormBuilder, private flightTemplateService: FlightTemplateService,
    private route: ActivatedRoute, private router: Router, private airportService: AirportService, private planeService: PlaneService, private notifierService: NotifierService) {
    this.notifier = notifierService;
  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.flightTemplateForm = this.fb.group({
      name: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      arrival_time: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      departure_time: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      arrival_airport: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      departure_airport: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
      plane: new FormControl({ value: '', disabled: this.areFormsDisabled }, [Validators.required]),
    })

    this.planeService.getAll().subscribe((planes) => {
      this.planes = planes;
    })

    this.airportService.getAllAirports().subscribe((airports) => {
      this.airports = airports;
    })

    this.flightTemplateId = +this.route.snapshot.paramMap.get('id');
    if (this.flightTemplateId != 0) {
      this.flightTemplateService.getFlightTemplateById(this.flightTemplateId).subscribe((flightTemplate) => {
        this.flightTemplate = flightTemplate;
        this.flightTemplateForm.setValue({
          name: this.flightTemplate.name, arrival_time: this.flightTemplate.arrivalTime,
          departure_time: this.flightTemplate.departureTime, arrival_airport: this.flightTemplate.arrivalAirport,
          departure_airport: this.flightTemplate.departureAirport, plane: this.flightTemplate.plane
        })
      })
    }
  }

  convertFormToObject() {
    const newFlightTemplate = new FlightTemplate();
    newFlightTemplate.name = this.flightTemplateForm.controls.name.value;
    newFlightTemplate.arrivalTime = this.flightTemplateForm.controls.arrival_time.value;
    newFlightTemplate.departureTime = this.flightTemplateForm.controls.departure_time.value;
    newFlightTemplate.arrivalAirport = this.flightTemplateForm.controls.arrival_airport.value
    newFlightTemplate.departureAirport = this.flightTemplateForm.controls.departure_airport.value
    newFlightTemplate.plane = this.flightTemplateForm.controls.plane.value;
    this.flightTemplate = newFlightTemplate;
    if (this.flightTemplateId != 0)
      this.flightTemplate.id = this.flightTemplateId;
  }

  isArrivalAirportSelected(arrivalAirportCode) {
    if (this.flightTemplate)
      if (this.flightTemplate.arrivalAirport.airportCode == arrivalAirportCode)
        return true;
    return false;
  }

  isDepartureAirportSelected(departureAirportCode) {
    if (this.flightTemplate)
      if (this.flightTemplate.departureAirport.airportCode == departureAirportCode)
        return true;
    return false;
  }

  isPlaneSelected(planeCode) {
    if (this.flightTemplate)
      if (this.flightTemplate.plane.code == planeCode)
        return true;
    return false;
  }
  onSubmit() {
    if (this.flightTemplateForm.valid) {
      this.convertFormToObject();
      this.flightTemplateEmitter.emit(this.flightTemplate);
    }
    else
      this.notifier.notify("error", "Invalid form, please fill in all required fields.")
  }

  onCancel() {
    this.router.navigate(["flight-template"]);
  }
}
