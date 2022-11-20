import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { HttpClientService, User } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public user: User = new User("","","","","","","","");

  constructor(private httpClientService: HttpClientService, private router: Router, private loginService: AuthenticationService) { }

  ngOnInit(): void {
    if (!this.loginService.isUserLoggedIn()) {
      this.router.navigate(['/login'])
    }
    else {
      this.httpClientService.getUser().subscribe(
        response => this.handleSuccessfulResponse(response),
      );
    }
  }

  deleteUser(user: User): void {
    this.httpClientService.deleteUser(user)
      .subscribe()
  }

  logOut() {
    this.router.navigate(['logout']);
  }

  handleSuccessfulResponse(response: User) {
    this.user = response;
  }

}
