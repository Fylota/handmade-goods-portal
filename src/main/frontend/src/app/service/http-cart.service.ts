import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class CartProduct {
  constructor(
    public id: string,
    public userId: string,
    public productId: string,
    public quantity: number
  ) { }
}

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
    /*
    this.httpClient.get<CartProduct[]>("http://localhost:8080/cart" + "/" + userId).subscribe(
      response => this.items = response
    );
    return this.items;
    */
  }

  removeItem(prod: CartProduct) {
    this.httpClient.delete<any>("http://localhost:8080/cart" + "/" + prod.id).subscribe(
      response => this.items = this.items.filter(e => e.id !== prod.id)
    );
  }

  clearCart(userId: string) {
    this.items.forEach(item =>{
      this.removeItem(item)
    });
    return this.items;
  }
}
