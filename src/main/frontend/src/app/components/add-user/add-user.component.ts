import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService, User } from 'src/app/service/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {

  user: User = new User("", "", "", "", "", "", "", "");

  constructor(private httpClientService: UserService, private router: Router) { }

  createUser(): void {
    this.httpClientService.createUser(this.user)
      .subscribe(_data => {
        alert("User created successfully.");
        this.router.navigate(['home']);
      });
  }

}
