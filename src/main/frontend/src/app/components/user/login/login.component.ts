import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/_shared/app.constants';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { CredentialResponse } from 'google-one-tap';
import jwt_decode from 'jwt-decode';
import { UserControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false
  googleURL = AppConstants.GOOGLE_AUTH_URL;

  constructor(private router: Router,
    private loginservice: AuthenticationService,
    private userService: UserControllerService,
    private _ngZone: NgZone) { }

  ngOnInit(): void {
    // @ts-ignore
    window.onGoogleLibraryLoad = () => {
      // @ts-ignore
      google.accounts.id.initialize({
        client_id: '820575053600-723ifuvc2elm5rv5rr35br63gvoh5o73.apps.googleusercontent.com',
        callback: this.handleCredentialResponse.bind(this),
        auto_select: false,
        cancel_on_tap_outside: true
      });
      // @ts-ignore
      google.accounts.id.renderButton(
        // @ts-ignore
        document.getElementById("googleBtnDiv"),
        { theme: "outline", size: "large", width: "100%"}
      );
      // @ts-ignore
      google.accounts.id.prompt((notification: PromptMomentNotification) => {});
    };
  }

  async handleCredentialResponse(response: CredentialResponse) {
    this.loginservice.LoginWithGoogle(response.credential).subscribe({
      next: (x: any) => {
        sessionStorage.setItem("token", 'Bearer ' + x.token);
        const tokenInfo = this.getDecodedAccessToken(x.token);
        console.log(tokenInfo)
        const roles = tokenInfo.authorities;
        sessionStorage.setItem('roles', roles);
        this._ngZone.run(() => {
          this.router.navigate(['/home']);
        });
      },
      error: (e) => console.error(e)
    })
  }

  checkLogin() {
    (this.loginservice.authenticate(this.username, this.password).subscribe({
      next: (_data) => {
        this.router.navigate(['home'])
        this.invalidLogin = false
      },
      error: (_error) => {
        this.invalidLogin = true
      }
    }));
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    } catch(Error) {
      console.log(Error);
      return null;
    }
  }

  goToRegisterPage() {
    this.router.navigate(['register'])
  }

  googleLogin() {
    window.open("http://localhost:8080/login/oauth2/code/google");
  }

  handleForgottenPsw() {
    this.router.navigate(['forgotPassword'])
  }
}
