import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartProduct, CartService } from 'src/app/service/cart.service';
import { UserService, User } from 'src/app/service/user.service';
import ProductService, { Product } from 'src/app/service/product.service';
import { Category } from 'src/app/models/category.model';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent implements OnInit {
  @Input() category?: Category;
  products: Product[] = [];
  user?: User;

  constructor(
    private httpProductService: ProductService,
    private router: Router,
    private cartService: CartService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    if (this.category !== undefined) {
      this.httpProductService.getProductsByCategory(this.category).subscribe(
        (response: any) => this.products = response._embedded.productList
      );
    } else {
      this.httpProductService.getProducts().subscribe(
        (response: any) => this.products = response._embedded.productList
      );
    }
    this.userService.getUser().subscribe(
      response => this.user = response
    );
  }

  navigateToProductDetails(product: Product) {
    this.router.navigate(['products/view'], {queryParams: {id:product.id}})
  }

  addToCart(productId: string) {
    let userId = this.user !== null ? this.user!.id : "";
    let cartProduct = new CartProduct("",userId, productId, 1);
    this.cartService.addToCart(cartProduct);
    window.alert('Your product has been added to the cart!');
  }
}
