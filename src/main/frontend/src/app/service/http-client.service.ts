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
    public address: string,
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private httpClient: HttpClient) { }
  getUsers() {
    return this.httpClient.get<User[]>('http://localhost:8080/users');
  }

  deleteUser(user: User) {
    return this.httpClient.delete<User>("http://localhost:8080/users" + "/" + user.id);
  }

  createUser(user: User) {
    return this.httpClient.post<User>("http://localhost:8080/register", user);
  }

  getUser() {
    return this.httpClient.get<User>('http://localhost:8080/users/me');
  }

  updateUser(user: User) {
    return this.httpClient.put<User>("http://localhost:8080/users" + "/" + user.id, user);
  }
}
