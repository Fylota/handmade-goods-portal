import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartProduct } from 'src/app/models/cart-product.model';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { CartService } from 'src/app/service/cart.service';
import { UserService, User } from 'src/app/service/user.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  user: User | undefined;
  products: any[] = [];
  subTotal = 0;

  constructor(
    private cartService: CartService,
    private userService: UserService,
    private authService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()) {
      this.userService.getUser().subscribe(
        response => {
          this.user = response;
          this.cartService.getItems(this.user.id).subscribe(response => 
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
      this.cartService.getItems(this.user.id)
        .subscribe(items => this.products = items);
    }
  }

  removeFromCart(prod: CartProduct) {
    this.products = this.products.filter(p => p !== prod);
    this.calcSubtotal();
    this.cartService.removeItem(prod).subscribe();
  }

  increaseQty(prod: any) {
    if (this.user !== undefined) {
      this.products = this.products.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity + 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSubtotal();
      this.cartService.updateItem({id: prod.id, userId: this.user.id, productId: prod.product.id, quantity: prod.quantity + 1}).subscribe();
    } 
  }

  decreaseQty(prod: any) {
    if (this.user !== undefined && prod.quantity > 1) {
      this.products = this.products.map(p => {
        if (p === prod) {
          return {...p, quantity: p.quantity - 1};
        }
        else {
          return {...p};
        }
      } );
      this.calcSubtotal();
      this.cartService.updateItem({id: prod.id, userId: this.user.id, productId: prod.product.id, quantity: prod.quantity - 1}).subscribe();
    }
  }


  calcSubtotal(): void {
    this.subTotal = this.products.reduce((subTot, prod) => 
      subTot + prod.quantity * prod.product.price,0
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
