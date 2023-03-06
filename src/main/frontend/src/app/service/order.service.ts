import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OrderCreate } from '../models/order-create';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) { }

  getOrders() {
    return this.httpClient.get('http://localhost:8080/orders');
  }

  addOrder(order: OrderCreate) {
    return this.httpClient.post('http://localhost:8080/orders', order) 
  }
}
