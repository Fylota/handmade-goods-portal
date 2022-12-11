import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CartProduct } from '../models/cart-product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  items: CartProduct[] = [];

  constructor(private httpClient: HttpClient) { }

  addToCart(prod: CartProduct) {
    this.httpClient.post<CartProduct>("http://localhost:8080/cart" + "/" + prod.userId, prod).subscribe(
      response => this.items.push(response)
    );
  }

  getItems(userId: string) {
    return this.httpClient.get<any>("http://localhost:8080/cart" + "/" + userId);
  }

  removeItem(prod: CartProduct) {
    this.httpClient.delete<any>("http://localhost:8080/cart" + "/" + prod.id).subscribe(
      () => this.items = this.items.filter(e => e.id !== prod.id)
    );
  }

  clearCart(userId: string) {
    this.items.forEach(item => {
      this.removeItem(item)
    });
    // todo send to backend
    return this.items;
  }
}
export { CartProduct };
