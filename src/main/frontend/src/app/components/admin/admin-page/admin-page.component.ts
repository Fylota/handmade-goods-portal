import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { faCircle, faCartShopping, faUserPen, faCalendar, faNewspaper, faPen, faPlus, faEnvelope, faTags } from '@fortawesome/free-solid-svg-icons'
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.scss']
})
export class AdminPageComponent {

  faCircle = faCircle;
  faCartShopping = faCartShopping;
  faUserPen = faUserPen;
  faCalendar = faCalendar;
  faNewspaper = faNewspaper;
  faPen = faPen;
  faPlus = faPlus;
  faEnvelope = faEnvelope;
  faTags = faTags;

  constructor(
    private router: Router,
    public translate: TranslateService
  ) { }

  navigateToPage(page: string): void {
    this.router.navigate([page]);
  }

}
