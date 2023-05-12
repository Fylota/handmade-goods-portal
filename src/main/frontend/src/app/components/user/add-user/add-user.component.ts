import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HomeControllerService, UserRegistrationDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {

  user: UserRegistrationDto = {};

  constructor(private homeService: HomeControllerService, private router: Router) { }

  createUser(): void {
    this.homeService.saveUser(this.user)
      .subscribe(_data => {
        alert("User created successfully.");
        this.router.navigate(['home']);
      });
  }

}
