import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category, HttpProductService } from 'src/app/service/http-product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  categories: Category[] = [];

  constructor(private httpProductService: HttpProductService, private router: Router) { }

  ngOnInit(): void {
    this.httpProductService.getCategories().subscribe(
      response => this.categories = response
    );
  }

  goToCategory(cat: Category) {
    const catName = cat ? cat.name : null;
    this.router.navigate(['products/category'], { queryParams: { name: catName }})
  }
}
