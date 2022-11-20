import { Component, Input, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  faSearch = faSearch;
  @Input() drawerRef: any;
  constructor(public loginService:AuthenticationService) { }

  ngOnInit(): void {
  }

}
