import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserControllerService, UserDto } from 'src/app/core/api/v1';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: UserDto = {
    firstName: '',
    email: ''
  };

  constructor(private httpClientService: UserControllerService, private router: Router, private loginService: AuthenticationService) { }

  ngOnInit(): void {
    if (!this.loginService.isUserLoggedIn()) {
      this.router.navigate(['/login'])
    }
    else {
      this.httpClientService.user().subscribe(
        response => this.handleSuccessfulResponse(response),
      );
    }
  }

  deleteUser(userId: string): void {
    this.httpClientService.deleteUser(Number(userId))
      .subscribe();
    
    this.loginService.logOut();
    this.router.navigate(['home']);
  }

  logOut() {
    this.router.navigate(['logout']);
  }

  handleSuccessfulResponse(response: UserDto) {
    this.user = response;
  }

  updateUser() {
    this.httpClientService.updateUser(Number(this.user.id), this.user)
    .subscribe(_data => {
      alert("User updated successfully.");
      this.router.navigate(['home']);
    });
  }
}
