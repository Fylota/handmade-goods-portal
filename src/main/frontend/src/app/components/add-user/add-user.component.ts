import { Component } from '@angular/core';
import { HttpClientService, User } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {

  user: User = new User("", "", "", "", "", "", "", "");

  constructor(private httpClientService: HttpClientService) { }

  createUser(): void {
    this.httpClientService.createUser(this.user)
      .subscribe(data => {
        alert("User created successfully.");
      });
  }

}
