import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
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

  @Output("closeEditing")
  closeEditing: EventEmitter<any> = new EventEmitter();
  @Output("newProductSaved")
  newProductSaved: EventEmitter<any> = new EventEmitter();
  @Output("updatedProduct")
  updatedProduct: EventEmitter<any> = new EventEmitter();

  constructor(private productService: ProductService, private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(
      response => this.categories = response
    );
    if (this.editProduct) {
      this.product = this.editProduct;
    }
  }

  handleSaveButton() {
    this.editProduct ? this.updateProduct() : this.createProduct();
  }

  createProduct() {
    this.productService.createProduct(this.product)
      .subscribe((newProd) => {
        console.log(this.product);
        alert("Product created successfully.");
        this.newProductSaved.emit(newProd);
      });
  }

  updateProduct() {
    this.productService.updateProduct(this.product)
      .subscribe((edited) => {
        console.log(this.product);
        alert("Product updated successfully.");
        this.updatedProduct.emit(edited);
      });
    this.editProduct = undefined;
  }

  cancelEditing() {
    this.closeEditing.emit();
  }

}
