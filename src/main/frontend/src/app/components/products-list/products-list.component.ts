import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartProduct, CartService } from 'src/app/service/http-cart.service';
import { HttpClientService, User } from 'src/app/service/http-client.service';
import { Category, HttpProductService, Product } from 'src/app/service/http-product.service';

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
    private httpProductService: HttpProductService,
    private router: Router,
    private cartService: CartService,
    private userService: HttpClientService
  ) { }

  ngOnInit(): void {
    if (this.category !== undefined) {
      this.httpProductService.getProductsByCategory(this.category).subscribe(
        response => this.products = response
      );
    } else {
      this.httpProductService.getProducts().subscribe(
        response => this.products = response
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
