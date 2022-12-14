import { Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/service/cart.service';
import { UserService, User } from 'src/app/service/user.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  user?: User;
  products: any[] = [];

  constructor(
    private cartService: CartService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe(
      response => {
        this.user = response;
        this.cartService.getItems(this.user.id).subscribe(
          response => this.products = response
        );
      }
    );

  }

}
