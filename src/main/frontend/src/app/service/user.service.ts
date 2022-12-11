import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {

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

export { User };
