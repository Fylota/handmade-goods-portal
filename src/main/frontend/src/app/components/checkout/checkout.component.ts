import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CartService } from 'src/app/service/cart.service';
import { User, UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  user: User | undefined;
  cartItems: any[] = [];
  subTotal = 0;
  shippingPrice = 0;
  orderTotal = 0;

  checkoutForm = new FormGroup({
    addressForm: new FormGroup({
      firstName: new FormControl(),
      lastName: new FormControl(),
      city: new FormControl(),
      postalCode: new FormControl(),
      phoneNumber: new FormControl(),
    }),
    shipmentForm: new FormGroup({
      shipment: new FormControl('inStore'),
    }),
    paymentForm: new FormGroup({
      paymentMethod: new FormControl('cash'),
    }),
  });

  constructor(
    private cartService: CartService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe(
      (user) => {
        this.user = user;
        this.checkoutForm.patchValue({
          addressForm: {
            firstName: user.firstName,
            lastName: user.lastName,
            city: user.address,
            postalCode: 'todo update User',
            phoneNumber: user.phoneNumber,
          }
        });
        this.cartService.getItems(user.id).subscribe(items => {
          this.cartItems = items;
          this.calcSummary();
        });
      }
    );
    this.onShipmentMethodChanges();
  }

  onShipmentMethodChanges(): void {
    this.checkoutForm.get('shipmentForm.shipment')?.valueChanges.subscribe(val => {
      this.shippingPrice = val === 'inStore' ? 0 : 500;
      this.calcSummary();
    });
  }

  removeFromCart(prod: any) {
    this.cartItems = this.cartItems.filter(p => p !== prod);
    this.calcSummary();
    this.cartService.removeItem(prod).subscribe();
  }

  increaseQty(prod: any) {
    if (this.user !== undefined) {
      this.cartItems = this.cartItems.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity + 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSummary();
      this.cartService.updateItem({id: prod.id, userId: this.user.id, productId: prod.product.id, quantity: prod.quantity + 1}).subscribe();
    }
  }

  decreaseQty(prod: any) {
    if (this.user !== undefined && prod.quantity > 1) {
      this.cartItems = this.cartItems.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity - 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSummary();
      this.cartService.updateItem({id: prod.id, userId: this.user.id, productId: prod.product.id, quantity: prod.quantity - 1}).subscribe();
    }
  }

  calcSummary(): void {
    this.subTotal = this.cartItems.reduce((subTot: number, prod: any) =>
      subTot + prod.quantity * prod.product.price,0
    );
    this.orderTotal = this.subTotal + this.shippingPrice;
  }

  onSubmit() {
    console.warn(this.checkoutForm.value);
  }

}
