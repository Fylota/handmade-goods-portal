import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private router: Router,
    private authService: AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot, url: any): boolean {
    if (this.authService.isUserLoggedIn()) {
      const userRole = this.authService.getRole();
      console.log(userRole);
      console.log(route.data['roles']);
      if (route.data['roles'] && userRole.indexOf(route.data['roles']) === -1) {
        this.router.navigate(['/home']);
        return false;
      }
      return true;
    }
    this.router.navigate(['login']);
    return false;
  }
}
