import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Category, CategoryControllerService, Product, ProductControllerService, ProductDto } from 'src/app/core/api/v1';

interface SortValue {
  value: string[];
  viewValue: string;
}

@Component({
  selector: 'app-manage-products',
  templateUrl: './manage-products.component.html',
  styleUrls: ['./manage-products.component.scss']
})
export class ManageProductsComponent implements OnInit {
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
  dataSource: MatTableDataSource<Product> = new MatTableDataSource<Product>();
  columnsToDisplay: string[] = ['id', 'name'];
  selectedProduct: Product | null = null;
  categories: Category[] = []; 

  constructor(
    private productService: ProductControllerService,
    private categoryService: CategoryControllerService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.refreshData();
    this.categoryService.getCategories().subscribe(
      response => this.categories = response
    );
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.refreshData();
  }

  refreshData(): void {
    this.productService.getProducts(this.pageIndex, this.pageSize, this.sort).subscribe((res: any) => {
      this.length = res["totalItems"];
      this.dataSource.data = res["products"];
    })
  }

  handleRowClicked(row: Product) {
    this.selectedProduct = row;
    console.log(row);
  }

  updateProduct() {
    if (this.selectedProduct !== null) {
      let dto: ProductDto = {
        name: this.selectedProduct.name,
        description: this.selectedProduct.description,
        price: this.selectedProduct.price,
        categoryId: this.selectedProduct.category?.id,
        imageURL: this.selectedProduct.imageURL
      };
      this.productService.updateProduct(this.selectedProduct.id!, dto ).subscribe({
        next: () => {
          this._snackBar.open("Product saved", "Dismiss");
          this.selectedProduct = null;
        },
        error: (e) => this._snackBar.open("Something went wrong :/", "Dismiss")
      });
    }
  }

  cancelEdit() {
    this.selectedProduct = null;
  }

  deleteProduct() {
    if (this.selectedProduct !== null) {
      this.productService.deleteProduct(this.selectedProduct.id!).subscribe({
        next: () => {
          this._snackBar.open("Product deleted", "Dismiss");
          this.selectedProduct = null;
          this.refreshData();
        },
        error: (e) => this._snackBar.open("Something went wrong :/", "Dismiss")
      });
    }
  }
}
