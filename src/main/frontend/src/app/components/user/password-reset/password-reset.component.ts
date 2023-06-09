import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
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
  resultMessage?: string;

  constructor(
    private route: ActivatedRoute,
    private userService: UserControllerService,
    private _snackBar: MatSnackBar,
    private translate: TranslateService
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
    if (this.newPassword !== this.matchingPassword) {
      this.translate.get('CHANGE_PSW.MATCH_ERROR').subscribe((res: string) => {
        this.resultMessage =  res;
        const dismiss = this.translate.instant('COMMON.SNACK_DISMISS');
        this.openSnackBar(this.resultMessage, dismiss);
      });
      throw Error("Passwords don't match");
    }
    this.userService.updatePassword(passwordDto).subscribe({
      next: () => {
        this.translate.get('CHANGE_PSW.SUCCESS').subscribe((res: string) => {
          this.resultMessage =  res;
          const dismiss = this.translate.instant('COMMON.SNACK_DISMISS');
          this.openSnackBar(this.resultMessage, dismiss);
        });
      },
      error: (error) => {
        this.translate.get('CHANGE_PSW.ERROR').subscribe((res: string) => {
          this.resultMessage =  res;
          const dismiss = this.translate.instant('COMMON.SNACK_DISMISS');
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
