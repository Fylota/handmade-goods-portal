import { Component, OnInit } from '@angular/core';
import { Category, CategoryControllerService, ProductControllerService, ProductDto } from 'src/app/core/api/v1';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-upload-product',
  templateUrl: './upload-product.component.html',
  styleUrls: ['./upload-product.component.scss']
})
export class UploadProductComponent implements OnInit {

  categories: Category[] = [];
  product: ProductDto = {};

  constructor(
    private productService: ProductControllerService,
    private categoryService: CategoryControllerService,
    private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(
      response => this.categories = response
    );
  }

  createProduct() {
    this.productService.addProduct(this.product)
      .subscribe(_ => {
        this._snackBar.open("Product saved", "Dismiss");
        this.product = {};
      });
  }

}
