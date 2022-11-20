import { Component, OnInit } from '@angular/core';
import { HttpClientService, User } from 'src/app/service/http-client.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public user: User = new User("","","","","","","","");

  constructor(private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService.getUser().subscribe(
      response => this.handleSuccessfulResponse(response),
    );
  }

  deleteUser(user: User): void {
    this.httpClientService.deleteUser(user)
      .subscribe()
  }

  handleSuccessfulResponse(response: User) {
    this.user = response;
  }

}
