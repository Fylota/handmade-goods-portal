import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CartProduct, CartProductDto, OrderControllerService, OrderCreateDto, UserControllerService, UserDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  user: UserDto | undefined;
  cartItems: CartProductDto[] = [];
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
    private userService: UserControllerService,
    private orderService: OrderControllerService
  ) { }

  ngOnInit(): void {
    this.userService.user().subscribe(
      (user) => {
        this.user = user;
        this.checkoutForm.patchValue({
          addressForm: {
            firstName: user.firstName,
            lastName: user.lastName,
            city: user.addresses ? user.addresses[0].city : '',
            postalCode: user.addresses ? user.addresses[0].zip : '',
            phoneNumber: user.phoneNumber,
          }
        });
        this.userService.getCartProducts(Number(user.id)).subscribe(items => {
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

  removeFromCart(prod: CartProduct) {
    this.cartItems = this.cartItems.filter(p => p !== prod);
    this.calcSummary();
    this.userService.removeCartProduct(Number(this.user!.id), prod.id!).subscribe();
  }

  increaseQty(prod: CartProduct) {
    if (this.user !== undefined) {
      this.cartItems = this.cartItems.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity! + 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSummary();
      this.userService.updateCartProduct(Number(this.user.id), prod.id!, {...prod, quantity: prod.quantity! + 1}).subscribe();
    }
  }

  decreaseQty(prod: any) {
    if (this.user !== undefined && prod.quantity > 1) {
      this.cartItems = this.cartItems.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity! - 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSummary();
      this.userService.updateCartProduct(Number(this.user.id), prod.id!, {...prod, quantity: prod.quantity! - 1}).subscribe();
    }
  }

  calcSummary(): void {
    this.subTotal = this.cartItems.reduce((subTot: number, prod: any) =>
      subTot + prod.quantity * prod.product.price,0
    );
    this.orderTotal = this.subTotal + this.shippingPrice;
  }

  onSubmit() {
    console.log(this.checkoutForm.value);
    console.log(this.user);
    console.log(this.cartItems);

    const newOrder: OrderCreateDto = {
      userId: this.user?.id,
      items: this.cartItems,
      paymentMethod: this.checkoutForm.value.paymentForm!.paymentMethod!,
      shippingMethod: this.checkoutForm.value.shipmentForm!.shipment!
    }
    this.orderService.addOrder(newOrder).subscribe(res => {
      console.log(res);
    });

  }

}
