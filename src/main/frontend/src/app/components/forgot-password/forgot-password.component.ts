import { Component } from '@angular/core';
import { UserControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {
  email = "";

  constructor(
    private userService: UserControllerService
  ) { }

  sendResetMail() {
    const encodedEmail = encodeURIComponent(this.email);
    this.userService.sendResetPasswordMail(encodedEmail).subscribe(res => console.log(res));
  }

}
