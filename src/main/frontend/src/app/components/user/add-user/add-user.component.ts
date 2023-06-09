import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { HomeControllerService, UserRegistrationDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {

  user: UserRegistrationDto = {};
  errors: string[] = [];

  constructor(private homeService: HomeControllerService,
    private router: Router,
    private _snackBar: MatSnackBar
  ) { }

  createUser(): void {
    this.homeService.saveUser(this.user)
      .subscribe({
        next: () => {
          this.errors = [];
          this._snackBar.open("Signed up! Please log in", "Dismiss");
          this.router.navigate(['profile']);
      },
      error: (e) => {
        this.errors = e.error.errors;
        console.log(e);
    }
    });
  }

}
