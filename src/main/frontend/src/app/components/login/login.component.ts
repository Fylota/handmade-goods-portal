import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false

  constructor(private router: Router,
    private loginservice: AuthenticationService) { }

  ngOnInit(): void {
  }

  checkLogin() {
    (this.loginservice.authenticate(this.username, this.password).subscribe({
      next: (data) => {
        this.router.navigate([''])
        this.invalidLogin = false
      },
      error: (error) => {
        this.invalidLogin = true
      }
    }));
  }
}
