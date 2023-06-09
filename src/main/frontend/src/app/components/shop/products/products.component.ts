import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category, CategoryControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  categories: Category[] = [];

  constructor(private categoryService: CategoryControllerService, private router: Router) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(
      response => this.categories = response
    );
  }

  goToCategory(cat: Category) {
    const catName = cat ? cat.name : null;
    this.router.navigate(['products/category'], { queryParams: { name: catName }})
  }
}
