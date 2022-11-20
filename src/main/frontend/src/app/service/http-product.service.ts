import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class Product {
  constructor(
    public id: string,
    public name: string,
    public price: number,
    public description: string,
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class HttpProductService {

  constructor(private httpClient: HttpClient) { }
  getProducts() {
    return this.httpClient.get<Product[]>('http://localhost:8080/product');
  }

  deleteProduct(product: Product) {
    return this.httpClient.delete<Product>("http://localhost:8080/product" + "/" + product.id);
  }

  createProduct(product: Product) {
    return this.httpClient.post<Product>("http://localhost:8080/product", product);
  }

  getProduct(productId: string) {
    return this.httpClient.get<Product>('http://localhost:8080/product' + '/view' + productId);
  }
}
