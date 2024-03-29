import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { Category } from './category.service';

@Injectable({
  providedIn: 'root'
})
export default class ProductService {
  constructor(private httpClient: HttpClient) { }

  createProduct(product: Product) {
    return this.httpClient.post<Product>("http://localhost:8080/products", product);
  }

  getProducts(): Observable<Product[]> {
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

  deleteProduct(id: string) {
    return this.httpClient.delete<Product>("http://localhost:8080/products" + "/" + id);
  }
}
export { Product };
