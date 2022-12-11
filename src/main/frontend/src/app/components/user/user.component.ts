import { Component, OnInit } from '@angular/core';
import { UserService, User } from 'src/app/service/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  users: User[] = [];

  constructor(private httpClientService: UserService) { }

  ngOnInit(): void {
    this.httpClientService.getUsers().subscribe(
      response => this.handleSuccessfulResponse(response),
    );
  }

  deleteUser(user: User): void {
    this.httpClientService.deleteUser(user)
      .subscribe( () => {
        this.users = this.users.filter(u => u !== user);
      })
  }

  handleSuccessfulResponse(response: User[]) {
    this.users = response;
  }

}
