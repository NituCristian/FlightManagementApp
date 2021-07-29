import { Company } from "../company/company";

export interface User{
    id: Number;
    activated: boolean;
	firstName: String;
    lastName: String;
    email: String;
    password: String;
    username: String;
    role : String;
    company: Company;

}