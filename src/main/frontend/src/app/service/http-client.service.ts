import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export class User {
  constructor(
    public id: string,
    public firstName: string,
    public lastName: string,
    public password: string,
    public matchingPassword: string,
    public email: string,
    public phoneNumber: string,
    public adress: string,
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private httpClient: HttpClient) { }
  getUsers() {
    return this.httpClient.get<User[]>('http://localhost:8080/user');
  }

  public deleteUser(user: User) {
    return this.httpClient.delete<User>("http://localhost:8080/user" + "/" + user.id);
  }

  public createUser(user: User) {
    return this.httpClient.post<User>("http://localhost:8080/user", user);
  }

  public getUser() {
    return this.httpClient.get<User>('http://localhost:8080/user/me');
  }
}
