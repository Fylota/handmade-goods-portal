import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { faCircle, faGear } from '@fortawesome/free-solid-svg-icons'

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.scss']
})
export class AdminPageComponent {

  faCircle = faCircle;
  faGear = faGear;

  constructor(
    private router: Router
  ) { }

  navigateToPage(page: String): void {
    this.router.navigate([page]);
  }

}
