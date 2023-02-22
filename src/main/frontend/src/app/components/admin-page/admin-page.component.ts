import { Component } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import ProductService from 'src/app/service/product.service';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.scss']
})
export class AdminPageComponent {

  activeTab = 0;
  allProducts: Product[] = [];
  editProduct: Product | undefined;

  constructor(
    private productService: ProductService,
  ) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe(
      (response: any) => this.allProducts = response._embedded.productList
    );
  }

  scrollToElement($element: any): void {
    console.log($element);
    $element.scrollIntoView({behavior: "smooth", block: "start", inline: "nearest"});
  }

  handleProductsTab(tabNumber: number): void {
    this.activeTab = tabNumber;
  }

  deleteItem(id: string) {
    this.allProducts = this.allProducts.filter(p => p.id !== id);
    this.productService.deleteProduct(id).subscribe();
  }

  editItem(id: string) {
    console.log("edititem in admin-page");
    this.productService.getProduct(id).subscribe(
      p => this.editProduct = p
    );
  }

}
