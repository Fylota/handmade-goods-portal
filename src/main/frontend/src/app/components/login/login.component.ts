import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  username = ''
  password = ''
  invalidLogin = false

  constructor(private router: Router,
    private loginservice: AuthenticationService) { }

  checkLogin() {
    (this.loginservice.authenticate(this.username, this.password).subscribe({
      next: (_data) => {
        this.router.navigate(['home'])
        this.invalidLogin = false
      },
      error: (_error) => {
        this.invalidLogin = true
      }
    }));
  }

  goToRegisterPage(){
    this.router.navigate(['register'])
  }
}
