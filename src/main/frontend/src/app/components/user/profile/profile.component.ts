import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AddressDto, UserControllerService, UserDto } from 'src/app/core/api/v1';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: UserDto = {
    firstName: '',
    email: '',
  };

  editContact = false;
  editAddress = false;

  selectedAddress?: AddressDto;

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

  setEditContact(value: boolean): void {
    this.editContact = value;
  }

  setEditAddress(value: boolean): void {
    this.editAddress = value;
  }

  setSelectedAddress(value: AddressDto) {
    this.selectedAddress = value;
  }

  deleteUser(userId: string): void {
    this.httpClientService.deleteUser(Number(userId)).subscribe();
    this.logOut();
  }

  logOut() {
    this.loginService.logOut();
    this.router.navigate(['home']);
  }

  handleSuccessfulResponse(response: UserDto) {
    this.user = response;
  }
}
