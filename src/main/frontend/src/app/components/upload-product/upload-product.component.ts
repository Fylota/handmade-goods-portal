import { Component, Input, OnInit } from '@angular/core';
import { Category, CategoryService } from 'src/app/service/category.service';
import ProductService, { Product } from 'src/app/service/product.service';

@Component({
  selector: 'app-upload-product',
  templateUrl: './upload-product.component.html',
  styleUrls: ['./upload-product.component.scss']
})
export class UploadProductComponent implements OnInit {
  @Input()
  editProduct: Product | undefined;

  categories: Category[] = [];
  product: Product = new Product("","",0,"","");

  constructor(private productService: ProductService, private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(
      response => this.categories = response
    );
    if (this.editProduct) {
      this.product = this.editProduct;
    }
  }

  createProduct() {
    this.productService.createProduct(this.product)
      .subscribe(() => {
        console.log(this.product);
        alert("Product created successfully.");
      });
  }

  updateProduct() {
    this.productService.updateProduct(this.product)
      .subscribe(() => {
        console.log(this.product);
        alert("Product created successfully.");
      });
  }

  SaveProduct() {
    this.editProduct ? this.updateProduct() : this.createProduct();
  }

  cancelEditing() {
    this.editProduct = undefined;
  }

}
