import { Component, OnInit } from '@angular/core';
import { Category, CategoryService } from 'src/app/service/category.service';
import ProductService, { Product } from 'src/app/service/product.service';

@Component({
  selector: 'app-upload-product',
  templateUrl: './upload-product.component.html',
  styleUrls: ['./upload-product.component.scss']
})
export class UploadProductComponent implements OnInit {

  categories: Category[] = [];
  product: Product = new Product("","",0,"","");

  constructor(private productService: ProductService, private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(
      response => this.categories = response
    );
  }

  createProduct() {
    this.productService.createProduct(this.product)
      .subscribe(() => {
        console.log(this.product);
        alert("Product created successfully.");
      });
  }

}
