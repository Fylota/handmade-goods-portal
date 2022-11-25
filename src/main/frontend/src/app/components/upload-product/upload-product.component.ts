import { Component, OnInit } from '@angular/core';
import { Category, HttpProductService, Product } from 'src/app/service/http-product.service';

@Component({
  selector: 'app-upload-product',
  templateUrl: './upload-product.component.html',
  styleUrls: ['./upload-product.component.scss']
})
export class UploadProductComponent implements OnInit {

  categories: Category[] = [];
  product: Product = new Product("","",0,"","");

  constructor(private productService: HttpProductService) { }

  ngOnInit(): void {
    this.productService.getCategories().subscribe(
      response => this.categories = response
    );
  }

  createProduct() {
    this.productService.createProduct(this.product)
      .subscribe(data => {
        console.log(this.product);
        alert("Product created successfully.");
      });
  }

}
