import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.scss']
})
export class AdminPageComponent {

  activeTab = 0;

  scrollToElement($element: any): void {
    console.log($element);
    $element.scrollIntoView({behavior: "smooth", block: "start", inline: "nearest"});
  }

  handleAdd() {
    this.activeTab = 0;
  }

  handleModify() {
    this.activeTab = 1;
  }

  handleCreateCat() {
    this.activeTab = 2;
  }

  handleModifyCat() {
    this.activeTab = 3;
  }

}
