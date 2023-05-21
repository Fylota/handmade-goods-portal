import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  CartProductDto,
  Product,
  ProductControllerService,
  UserControllerService,
  UserDto,
  ReviewDto
} from 'src/app/core/api/v1';
import { AuthenticationService } from 'src/app/service/authentication.service';

import { faStar } from '@fortawesome/free-solid-svg-icons';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss'],
})
export class ProductDetailsComponent {
  param: number | undefined;
  product: Product | undefined;
  user: UserDto | undefined;
  // reviews$: Observable<ReviewDto[]> = new Observable<ReviewDto[]>;
  reviews: ReviewDto[] = [];
  editReview: ReviewDto = {};
  reviewContent = "";
  loggedIn = false;
  faStar = faStar;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductControllerService,
    private authService: AuthenticationService,
    private userService: UserControllerService,
    private _snackBar: MatSnackBar
  ) {
    this.route.queryParams.subscribe((params) => {
      this.param = Number(params['id']);
      if (this.param != undefined) {
        this.reloadReviews(this.param)
        this.productService.getProduct(this.param).subscribe((res) => {
          this.product = res;
        });
      }
    });
    if (this.authService.isUserLoggedIn()) {
      this.loggedIn = true;
      this.userService.user().subscribe((response) => {
        this.user = response;
        this.editReview.productId = this.product?.id;
        this.editReview.userId = Number(this.user?.id);
        this.editReview.reviewerName = this.user?.firstName ? this.user?.firstName : "Anonymus reviewer";
      });
    }
  }

  reloadReviews(productId: number): void {
    this.productService.getReviews(productId).subscribe(res => this.reviews = res)
  }

  addToCart() {
    if (!this.loggedIn) {
      window.alert('You must log in first!');
    } else {
      let userId = Number(this.user!.id);
      let cartProduct: CartProductDto = {
        productId: this.product!.id!,
        quantity: 1,
      };
      this.userService.addCartProduct(userId, cartProduct).subscribe(() => this._snackBar.open("Added to cart!", "Ok"));
    }
  }

  addToWishlist() {
    this.userService.addToWishList(Number(this.user!.id), this.product!.id!).subscribe(() => this._snackBar.open("Added to wishlist!", "Ok"));
  }

  postReview() {
    if (this.product?.id !== undefined && this.user !== undefined) {
      this.editReview.content = this.reviewContent;
      this.productService.addReview(this.product?.id, this.editReview).subscribe(_ => this.reloadReviews(this.product?.id!))
    }
  }

}
