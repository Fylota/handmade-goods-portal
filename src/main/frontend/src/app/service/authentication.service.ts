import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable } from 'rxjs';

export class UserStatus {
  constructor(public status: string) { }
}

export class JwtResponse {
  constructor(public jwttoken: string) { }
}

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  authenticate(username: string, password: string) {
    return this.httpClient.post<any>('http://localhost:8080/authenticate',{username,password}).pipe(
     map(
       userData => {
        sessionStorage.setItem('username',username);
        let tokenStr= 'Bearer '+userData.token;
        sessionStorage.setItem('token', tokenStr);
        return userData;
       }
     )

    );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    console.log(user !== null)
    return user !== null
  }

  logOut(): Observable<any> {
    sessionStorage.removeItem('username');
    this.router.navigate(['logout']);
    return this.httpClient.post('http://localhost:8080/logout', { }, httpOptions);
  }
}
