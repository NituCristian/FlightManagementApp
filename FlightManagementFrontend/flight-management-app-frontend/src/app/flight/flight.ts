import { Plane } from "../plane/plane";
import { FlightHistory } from "./flight-history";

export interface Flight {
    id: Number;
    arrivalTime: Date;
    departureTime: Date;
    plane: Plane;
	flightHistories: FlightHistory[];
	
}