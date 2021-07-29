import {User} from '../user/user';

export interface FlightHistory {
    id: Number;
	previousDepartureTime : Date;
	previousArrivalTime : Date;
	newDepartureTime : Date;
	newArrivalTime : Date;
	operation: Date;
	modifiedOn: Date;
	user: User;

}