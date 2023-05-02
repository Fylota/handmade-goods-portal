import { Component} from '@angular/core';
import { UserControllerService } from 'src/app/core/api/v1';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {
  email = "";

  resultMessage?: string;
  durationInSeconds = 5;

  constructor(
    private userService: UserControllerService,
    private _snackBar: MatSnackBar,
    private translate: TranslateService
  ) { }

  sendResetMail() {
    const encodedEmail = encodeURIComponent(this.email);
    this.userService.sendResetPasswordMail(encodedEmail).subscribe({
      next: () => {
        this.translate.get('FORGOT.SUCCESS').subscribe((res: string) => {
          this.resultMessage =  res;
          const dismiss = this.translate.instant('FORGOT.SNACK_DISMISS');
          this.openSnackBar(this.resultMessage, dismiss);
        });
      },
      error: (error) => {
        this.translate.get('FORGOT.ERROR').subscribe((res: string) => {
          this.resultMessage =  res;
          const dismiss = this.translate.instant('FORGOT.SNACK_DISMISS');
          this.openSnackBar(this.resultMessage, dismiss);
        });
        throw error;
      }
    });
    
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

}
