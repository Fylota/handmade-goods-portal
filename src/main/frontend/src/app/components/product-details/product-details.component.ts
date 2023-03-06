import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartProduct } from 'src/app/models/cart-product.model';
import { Product } from 'src/app/models/product.model';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { CartService } from 'src/app/service/cart.service';
import ProductService from 'src/app/service/product.service';
import { User, UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent {

  param = "";
  product: Product | undefined;
  user: User | undefined;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private authService: AuthenticationService,
    private userService: UserService) {
    this.route.queryParams.subscribe(params => {
      this.param = params['id'];
      productService.getProduct(this.param).subscribe(res => {
        this.product = res;
        console.log(this.product);
      });
    });
    if (this.authService.isUserLoggedIn()) {
      this.userService.getUser().subscribe(
        response => this.user = response
      );
    }
  }

  addToCart() {
    if (!this.authService.isUserLoggedIn()) {
      window.alert('You must log in first!');
      // TODO temp cart
    } else {
      let userId = this.user !== null ? this.user!.id : "";
      let cartProduct = new CartProduct("",userId, this.product!.id, 1);
      this.cartService.addToCart(cartProduct).subscribe();
      window.alert('Your product has been added to the cart!');
    }
  }

}
