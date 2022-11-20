import { Component, OnInit } from '@angular/core';
import { HttpProductService, Product } from 'src/app/service/http-product.service';

@Component({
  selector: 'app-upload-product',
  templateUrl: './upload-product.component.html',
  styleUrls: ['./upload-product.component.scss']
})
export class UploadProductComponent implements OnInit {

  product: Product = new Product("","",0,"");

  constructor(private productService: HttpProductService) { }

  ngOnInit(): void {
  }

  createProduct() {
    this.productService.createProduct(this.product)
      .subscribe(data => {
        alert("User created successfully.");
      });
  }

}
