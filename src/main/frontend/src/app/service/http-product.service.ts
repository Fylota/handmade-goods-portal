import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from './http-category.service';

export class Product {
  constructor(
    public id: string,
    public name: string,
    public price: number,
    public description: string,
    public category: string,
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class HttpProductService {
  constructor(private httpClient: HttpClient) { }

  createProduct(product: Product) {
    return this.httpClient.post<Product>("http://localhost:8080/products", product);
  }

  getProducts() {
    return this.httpClient.get<Product[]>('http://localhost:8080/products');
  }

  getProduct(productId: string) {
    return this.httpClient.get<Product>('http://localhost:8080/products' + "/" + productId);
  }

  getProductsByCategory(category: Category) {
    return this.httpClient.get<Product[]>('http://localhost:8080/products' + '/category/' + category.id);
  }

  updateProduct(product: Product) {
    return this.httpClient.put<Product>('http://localhost:8080/products' + "/" + product.id, product);
  }

  deleteProduct(product: Product) {
    return this.httpClient.delete<Product>("http://localhost:8080/product" + "/" + product.id);
  }
}
