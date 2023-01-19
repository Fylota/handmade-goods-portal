import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthenticationService } from './service/authentication.service';
import { EventBusService } from './service/event-bus.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  eventBusSub?: Subscription;

  constructor(
    private authService: AuthenticationService,
    private eventBusService: EventBusService
  ) {}

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
