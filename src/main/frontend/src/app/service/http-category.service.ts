import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class Category {
  constructor(
    public id: string,
    public name: string,
  ) { }
}

@Injectable({
  providedIn: 'root'
})

export class HttpCategoryService {

  constructor(private httpClient: HttpClient) { }

  getCategories() {
    return this.httpClient.get<Category[]>('http://localhost:8080/product/category');
  }

  getCategoryByName(name: string) {
    return this.httpClient.get<Category>('http://localhost:8080/product/category/view' + '/' + name);
  }
}
