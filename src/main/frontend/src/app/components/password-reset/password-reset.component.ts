import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PasswordDto, UserControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent implements OnInit {
  token = "";
  email = "";
  newPassword = "";
  matchingPassword = "";

  constructor(
    private route: ActivatedRoute,
    private userService: UserControllerService,
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      console.log(this.token);
    });
  }

  savePassword() {
    console.log(this.email, this.newPassword, this.matchingPassword, this.token);
    const passwordDto: PasswordDto = {
      token: this.token,
      newPassword: this.newPassword
    }
    this.userService.updatePassword(passwordDto).subscribe({
      next: (response) => {
        console.log('response received')
        console.log(response)
      },
      error: (error) => {
        console.error('error caught in component')
        throw error;
      }
    })
  }

}
