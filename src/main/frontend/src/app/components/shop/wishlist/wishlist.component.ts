import { Component, OnInit } from '@angular/core';
import { UserDto, UserControllerService, ProductDto, CartProductDto } from 'src/app/core/api/v1';
import { faX } from '@fortawesome/free-solid-svg-icons';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {
  user: UserDto | undefined;
  products: ProductDto[] = [];
  faX = faX;

  constructor(
    private userService: UserControllerService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.userService.user().subscribe(u => {
      this.user = u;
      this.refreshData();
    });
  }

  refreshData() {
    this.userService.getWishList(Number(this.user!.id)).subscribe(prods => {
      console.log(prods);
      this.products = prods;
    })
  }

  removeFromWishlist(item: ProductDto) {
    if (this.user !== undefined) {
      this.userService.removeFromWishList(Number(this.user.id), item.id!).subscribe(
        () => this.refreshData()
      );
    }
  }

  addToCart(item: ProductDto) {
    if (this.user !== undefined) {
      const cartProductDto: CartProductDto = {
        productId: item.id,
        quantity: 1,
      };
      this.userService.addCartProduct(Number(this.user.id), cartProductDto).subscribe(() => {
        this._snackBar.open("Added to cart!", "Ok");
        this.removeFromWishlist(item);
        this.refreshData();
      });
    }
  }
}
