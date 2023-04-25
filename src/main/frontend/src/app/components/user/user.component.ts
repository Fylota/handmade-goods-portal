import { Component, OnInit } from '@angular/core';
import { UserControllerService, UserDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  users: UserDto[] = [];

  constructor(private httpClientService: UserControllerService) { }

  ngOnInit(): void {
    this.httpClientService.getUsers().subscribe(
      response => this.handleSuccessfulResponse(response),
    );
  }

  deleteUser(user: UserDto): void {
    this.httpClientService.deleteUser(Number(user.id!))
      .subscribe( () => {
        this.users = this.users.filter(u => u !== user);
      })
  }

  handleSuccessfulResponse(response: UserDto[]) {
    this.users = response;
  }

}
