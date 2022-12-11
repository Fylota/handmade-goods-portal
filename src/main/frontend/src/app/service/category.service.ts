import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../models/category.model';

@Injectable({
  providedIn: 'root'
})

export class CategoryService {

  constructor(private httpClient: HttpClient) { }

  getCategories() {
    return this.httpClient.get<Category[]>('http://localhost:8080/categories');
  }

  getCategoryByName(name: string) {
    return this.httpClient.get<Category>('http://localhost:8080/categories' + '/' + name);
  }
}
export { Category };
