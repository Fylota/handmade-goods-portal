import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import jwt_decode from 'jwt-decode';

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
  private adminStatus = new BehaviorSubject<boolean>(false);
  public admin$ = this.adminStatus.asObservable();
  roleAs = '';

  constructor(private httpClient: HttpClient, private router: Router) { }

  authenticate(username: string, password: string) {
    return this.httpClient.post<any>('http://localhost:8080/authenticate',{username,password}).pipe(
     map(
       userData => {
        console.log(userData);
        //sessionStorage.setItem('username',username);
        let tokenStr= 'Bearer '+userData.token;
        sessionStorage.setItem('token', tokenStr);
        const tokenInfo = this.getDecodedAccessToken(userData.token);
        console.log(tokenInfo)
        const roles = tokenInfo.authorities;
        sessionStorage.setItem('roles', roles);
        this.adminStatus.next(this.isAdmin());
        return userData;
       }
     )

    );
  }

  LoginWithGoogle(credentials: string): Observable<any> {
    return this.httpClient.post('http://localhost:8080/auth/google', JSON.stringify(credentials), httpOptions);
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('token')
    return user !== null
  }

  logOut(): Observable<any> {
    sessionStorage.removeItem('token');
    sessionStorage.setItem('roles', '');
    this.router.navigate(['logout']);
    this.adminStatus.next(this.isAdmin());
    return this.httpClient.post('http://localhost:8080/logout', { }, httpOptions);
  }

  getRole() {
    this.roleAs = sessionStorage.getItem('roles') != null ? sessionStorage.getItem('roles')! : '';
    return this.roleAs;
  }

  isAdmin(): boolean {
    return this.getRole().indexOf('ROLE_ADMIN') !== -1;
  }

  getAdmin(): Observable<boolean> {
    this.adminStatus.next(this.isAdmin());
    return this.admin$;
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch(Error) {
      return null;
    }
  }
}
