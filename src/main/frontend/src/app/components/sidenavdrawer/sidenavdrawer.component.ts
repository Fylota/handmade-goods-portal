import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidenavdrawer',
  templateUrl: './sidenavdrawer.component.html',
  styleUrls: ['./sidenavdrawer.component.scss']
})
export class SidenavdrawerComponent implements OnInit {
  @Input() drawer: any;
  constructor() { }

  ngOnInit(): void {
  }

  closeDrawer(){
    this.drawer.close();
  }

}
