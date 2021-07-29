import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { UserCredentials } from '../usercredentials';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string = "";
  public password: string = "";

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }

  login(): void {
    let userCredentials: UserCredentials = {
      "username": this.username,
      "password": this.password
    }
    this.loginService.authenticate(userCredentials);
    this.username = "";
    this.password = "";
  }
}


