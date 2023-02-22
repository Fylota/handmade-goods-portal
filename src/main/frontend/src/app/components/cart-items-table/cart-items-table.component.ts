import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faX } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { faMinus } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-cart-items-table',
  templateUrl: './cart-items-table.component.html',
  styleUrls: ['./cart-items-table.component.scss']
})
export class CartItemsTableComponent implements OnInit {

  @Input()
  products: any;

  faX = faX;
  faPlus = faPlus;
  faMinus = faMinus;

  @Output("removeFromCart")
  removeFromCart: EventEmitter<any> = new EventEmitter();
  @Output("decreaseQty")
  decreaseQty: EventEmitter<any> = new EventEmitter();
  @Output("increaseQty")
  increaseQty: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  emitRemoveFromCart(item: any) {
    this.removeFromCart.emit(item);
  }

  emitDecreaseQty(item: any) {
    this.decreaseQty.emit(item);
  }

  emitIncreaseQty(item: any) {
    this.increaseQty.emit(item);
  }

}
