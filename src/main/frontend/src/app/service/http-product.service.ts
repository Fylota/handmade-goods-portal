import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class Product {
  constructor(
    public id: string,
    public name: string,
    public price: number,
    public description: string,
    public category: string,
  ) { }
}

export class Category {
  constructor(
    public id: string,
    public name: string,
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class HttpProductService {
  constructor(private httpClient: HttpClient) { }

  createProduct(product: Product) {
    return this.httpClient.post<Product>("http://localhost:8080/product", product);
  }

  getProducts() {
    return this.httpClient.get<Product[]>('http://localhost:8080/product');
  }

  getProduct(productId: string) {
    return this.httpClient.get<Product>('http://localhost:8080/product' + '/view/' + productId);
  }

  getProductsByCategory(category: Category) {
    return this.httpClient.get<Product[]>('http://localhost:8080/product' + '/category/' + category.id);
  }

  //todo update product

  deleteProduct(product: Product) {
    return this.httpClient.delete<Product>("http://localhost:8080/product" + "/" + product.id);
  }

  getCategories() {
    return this.httpClient.get<Category[]>('http://localhost:8080/product/category');
  }

  getCategoryByName(name: string) {
    return this.httpClient.get<Category>('http://localhost:8080/product/category/view' + '/' + name);
  }

}
