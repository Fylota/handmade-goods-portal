import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isAdmin = false;

  constructor(
    public translate: TranslateService,
    private authService: AuthenticationService
  ) { 
  }

  ngOnInit():void {
    this.authService.admin$.subscribe(status => {
      this.isAdmin = status
    })
  }

}
