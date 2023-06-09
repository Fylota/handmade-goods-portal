import { Component, OnChanges, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartProduct, UserControllerService, UserDto } from 'src/app/core/api/v1';
import { AuthenticationService } from 'src/app/service/authentication.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit, OnChanges {
  user: UserDto | undefined;
  products: CartProduct[] = [];
  subTotal = 0;

  constructor(
    private userService: UserControllerService,
    private authService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()) {
      this.userService.user().subscribe(
        response => {
          this.user = response;
          this.userService.getCartProducts(Number(this.user.id)).subscribe(response => 
            {
              this.products = response;
              this.calcSubtotal();
            });
        }
      );
    } else {
      window.alert('You must log in first!');
    }
  }

  ngOnChanges() {
    this.calcSubtotal();
  }

  getCartProducts(): void {
    if (this.user) {
      this.userService.getCartProducts(Number(this.user.id))
        .subscribe(items => this.products = items);
    }
  }

  removeFromCart(prod: CartProduct) {
    this.products = this.products.filter(p => p !== prod);
    this.calcSubtotal();
    this.userService.removeCartProduct(Number(this.user!.id), prod.id!).subscribe();
  }

  increaseQty(prod: any) {
    if (this.user !== undefined) {
      this.products = this.products.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity! + 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSubtotal();
      this.userService.updateCartProduct(Number(this.user.id), prod.id, {...prod, quantity: prod.quantity! + 1}).subscribe();
    } 
  }

  decreaseQty(prod: any) {
    if (this.user !== undefined && prod.quantity > 1) {
      this.products = this.products.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity! - 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSubtotal();
      this.userService.updateCartProduct(Number(this.user.id), prod.id, {...prod, quantity: prod.quantity! - 1}).subscribe();
    }
  }


  calcSubtotal(): void {
    this.subTotal = this.products.reduce((subTot, prod) => 
      subTot + prod.quantity! * prod.product!.price!, 0
    );
  }

  navigateToHome(): void {
    this.router.navigate(["home"]);
  }

  navigateToProducts() {
    this.router.navigate(['products']);
  }

  navigateToCheckout() {
    this.router.navigate(['checkout']);
  }
}
