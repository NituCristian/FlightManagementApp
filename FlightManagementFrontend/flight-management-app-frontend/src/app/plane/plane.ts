import { Company } from "../company/company";
export interface Plane{
    id: Number;
	name:String;
    code: String;
    model: String;
    numberOfPassengers: Number;
    company: Company;
}