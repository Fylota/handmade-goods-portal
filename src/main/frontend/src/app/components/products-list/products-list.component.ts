import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { faCartPlus } from '@fortawesome/free-solid-svg-icons';
import {
  CartProductDto,
  Category,
  Product,
  ProductControllerService,
  UserControllerService,
} from 'src/app/core/api/v1';
import { PageEvent } from '@angular/material/paginator';

interface SortValue {
  value: string[];
  viewValue: string;
}

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss'],
})
export class ProductsListComponent implements OnInit {
  @Input() category?: Category;
  products: Product[] | undefined;
  length = 10;
  pageSize = 10;
  pageSizeOptions = [5, 10, 25];
  pageIndex = 0;
  sort = ["name", "asc"];
  pageEvent: PageEvent | undefined;
  sortValues: SortValue[] = [
    {value: ["name", "asc"], viewValue: 'Name ascending'},
    {value: ["name", "desc"], viewValue: 'Name descending'},
  ]

  user$ = this.userService.user();
  userId = 0;
  faCartPlus = faCartPlus;

  constructor(
    private productService: ProductControllerService,
    private router: Router,
    private authService: AuthenticationService,
    private userService: UserControllerService
  ) {}

  ngOnInit(): void {
    if (this.category !== undefined) {
      this.productService.getProductsByCategory(this.category.id!).subscribe((res: any) => {
        this.products = res;
      })
    } else {
      this.productService.getProducts(this.pageIndex, this.pageSize, this.sort).subscribe((res: any) => {
        this.products = res["products"];
        this.length = res["totalItems"];
      })
    }
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.productService.getProducts(this.pageIndex, this.pageSize, this.sort).subscribe((res: any) => {
      this.products = res["products"];
      this.length = res["totalItems"];
    })
  }

  handleSelectEvent() {
    this.productService.getProducts(this.pageIndex, this.pageSize, this.sort).subscribe((res: any) => {
      this.products = res["products"];
      this.length = res["totalItems"];
    })
  }

  navigateToProductDetails(product: Product) {
    this.router.navigate(['products/view'], {
      queryParams: { id: product.id },
    });
  }

  addToCart(productId: number) {
    if (!this.authService.isUserLoggedIn()) {
      window.alert('You must log in first!');
      // TODO temp cart
    } else {
      this.user$.subscribe((user) => {
        this.userId = Number(user.id!);
        const cartProductDto: CartProductDto = {
          productId: productId,
          quantity: 1,
        };
        this.userService.addCartProduct(this.userId, cartProductDto).subscribe();
        window.alert('Your product has been added to the cart!');
      });
    }
  }
}
