import { Component } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { OrderService } from 'src/app/service/order.service';
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
    private orderService: OrderService,
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

  closeEditing() {
    this.editProduct = undefined;
  }

  updateItem(updatedProduct: Product) {
    this.allProducts = this.allProducts.map(p => {
      if (p.id === updatedProduct.id) {
        return updatedProduct;
      } else {
        return p;
      }
    })
    this.closeEditing();
  }

  saveItem(newItem: Product) {
    this.allProducts = [...this.allProducts, newItem];
    this.closeEditing();
  }

  getOrders() {
    console.log(this.orderService.getOrders().subscribe());
  }

}
