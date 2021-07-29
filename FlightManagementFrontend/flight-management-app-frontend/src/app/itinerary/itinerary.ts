import { Airport } from "../airport/Airport";

export interface Itinerary {
    id: Number;
    departureAirport: Airport;
    arrivalAirport: Airport;
}