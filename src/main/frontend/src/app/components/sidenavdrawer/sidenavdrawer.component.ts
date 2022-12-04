import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-sidenavdrawer',
  templateUrl: './sidenavdrawer.component.html',
  styleUrls: ['./sidenavdrawer.component.scss']
})
export class SidenavdrawerComponent {
  @Input() drawer: any;
  constructor() { }

  closeDrawer(){
    this.drawer.close();
  }

}
