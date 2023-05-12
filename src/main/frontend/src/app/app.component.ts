import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthenticationService } from './service/authentication.service';
import { EventBusService } from './service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  eventBusSub?: Subscription;

  constructor(
    private authService: AuthenticationService,
    private eventBusService: EventBusService,
    public translate: TranslateService
  ) {
    translate.setDefaultLang('en');
    translate.addLangs(['en', 'hu']);
    let browserLang = translate.getBrowserLang();
    browserLang ??= 'en';
    translate.use(browserLang.match(/en|fr/) ? browserLang : 'en');
  }

  ngOnInit(): void {
    this.eventBusSub = this.eventBusService.on('logout', () => {
      this.logout();
    });
  }

  logout(): void {
    this.authService.logOut().subscribe({
      next: (res: any) => {
        console.log(res);
        window.location.reload();
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }
  
}
