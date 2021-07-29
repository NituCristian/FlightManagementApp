import { Airport } from "../airport/Airport";
import { Plane } from "../plane/plane";

export class FlightTemplate {

    id: Number;
    name: String;
    arrivalTime: String;
    departureTime: String;
    arrivalAirport: Airport;
    departureAirport: Airport;
    plane: Plane;
}