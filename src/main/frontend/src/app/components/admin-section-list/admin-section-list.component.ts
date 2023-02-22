import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Product } from 'src/app/models/product.model';

@Component({
  selector: 'app-admin-section-list',
  templateUrl: './admin-section-list.component.html',
  styleUrls: ['./admin-section-list.component.scss']
})
export class AdminSectionListComponent implements OnInit {

  @Input()
  items: Product[] | undefined;

  headers = ["id", "name", "actions"];

  @Output("deleteItem")
  deleteItem: EventEmitter<any> = new EventEmitter();
  @Output("editItem")
  editItem: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  emitDelete(id: string) {
    this.deleteItem.emit(id);
  }

  emitEdit(id: string) {
    console.log("edititem in admin-section");
    this.editItem.emit(id);
  }

}
