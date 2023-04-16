import { Component, Input } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  faSearch = faSearch;
  @Input() drawerRef: any;
  constructor(public loginService:AuthenticationService, public translate: TranslateService) { }


}
