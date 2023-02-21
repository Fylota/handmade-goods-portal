import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { CartProduct } from '../models/cart-product.model';
import { HttpErrorHandler, HandleError } from '../service/http-error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private handleError: HandleError;

  constructor(
    private httpClient: HttpClient,
    httpErrorHandler: HttpErrorHandler) {
      this.handleError = httpErrorHandler.createHandleError('CartService');
    }

  addToCart(prod: CartProduct): Observable<CartProduct> {
    return this.httpClient.post<CartProduct>("http://localhost:8080/cart", prod)
    .pipe(
      catchError(this.handleError('addToCart', prod))
    );
  }

  getItems(userId: string): Observable<CartProduct[]> {
    return this.httpClient.get<CartProduct[]>("http://localhost:8080/cart" + "/" + userId)
      .pipe(
        catchError(this.handleError('getItems', []))
      );
  }

  removeItem(prod: CartProduct): Observable<unknown> {
    return this.httpClient.delete("http://localhost:8080/cart" + "/" + prod.id)
      .pipe(
        catchError(this.handleError('removeItem'))
      );
  }

  updateItem(prod: CartProduct): Observable<CartProduct> {
    return this.httpClient.put<CartProduct>("http://localhost:8080/cart", prod)
      .pipe(
        catchError(this.handleError('updateItem', prod))
      );
  }

  clearCart(userId: string) {
    //TODO
  }

}
