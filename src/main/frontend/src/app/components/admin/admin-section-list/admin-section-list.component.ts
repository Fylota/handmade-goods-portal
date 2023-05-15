import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Product } from 'src/app/core/api/v1';

@Component({
  selector: 'app-admin-section-list',
  templateUrl: './admin-section-list.component.html',
  styleUrls: ['./admin-section-list.component.scss']
})
export class AdminSectionListComponent {

  @Input()
  items: Product[] | undefined;

  headers = ["id", "name", "actions"];

  @Output()
  deleteItem: EventEmitter<any> = new EventEmitter();
  @Output()
  editItem: EventEmitter<any> = new EventEmitter();

  emitDelete(id: number) {
    this.deleteItem.emit(id);
  }

  emitEdit(id: number) {
    console.log("edititem in admin-section");
    this.editItem.emit(id);
  }

}
