import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClientService, User } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {

  user: User = new User("", "", "", "", "", "", "", "");

  constructor(private httpClientService: HttpClientService, private router: Router) { }

  createUser(): void {
    this.httpClientService.createUser(this.user)
      .subscribe(_data => {
        alert("User created successfully.");
        this.router.navigate(['home']);
      });
  }

}
