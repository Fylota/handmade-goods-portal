import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category, HttpProductService, Product } from 'src/app/service/http-product.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent implements OnInit {
  @Input() category?: Category;
  products: Product[] = [];


  constructor(private httpProductService: HttpProductService, private router: Router) { }

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
  }

  navigateToProductDetails(product: Product) {
    this.router.navigate(['products/view'], {queryParams: {id:product.id}})
  }
}
