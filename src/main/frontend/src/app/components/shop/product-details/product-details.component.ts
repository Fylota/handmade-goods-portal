import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import {
  CartProductDto,
  Product,
  ProductControllerService,
  UserControllerService,
  UserDto,
  ReviewDto
} from 'src/app/core/api/v1';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss'],
})
export class ProductDetailsComponent {
  param: number | undefined;
  product: Product | undefined;
  user: UserDto | undefined;
  reviews$: Observable<ReviewDto[]> | undefined;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductControllerService,
    private authService: AuthenticationService,
    private userService: UserControllerService
  ) {
    this.route.queryParams.subscribe((params) => {
      this.param = Number(params['id']);
      if (this.param != undefined) {
        this.reviews$ =  productService.getReviews(this.param);
        this.productService.getProduct(this.param).subscribe((res) => {
          this.product = res;
        });
      }
    });
    if (this.authService.isUserLoggedIn()) {
      this.userService.user().subscribe((response) => (this.user = response));
    }
  }

  addToCart() {
    if (!this.authService.isUserLoggedIn()) {
      window.alert('You must log in first!');
      // TODO temp cart
    } else {
      let userId = Number(this.user!.id);
      let cartProduct: CartProductDto = {
        productId: this.product!.id!,
        quantity: 1,
      };
      //"",userId, this.product!.id, 1);
      this.userService.addCartProduct(userId, cartProduct).subscribe();
      window.alert('Your product has been added to the cart!');
    }
  }
}
