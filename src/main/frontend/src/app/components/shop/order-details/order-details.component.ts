import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderControllerService, OrderItemDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent {
  param = "";
  order?: OrderItemDto;
  constructor(
    private route: ActivatedRoute,
    private orderService: OrderControllerService
  ) {
    this.route.queryParams.subscribe((params) => {
      this.param = params['id'];
      orderService.getOrderById(Number(this.param)).subscribe(res => {
        console.log(res);
        this.order = res;
      });
    });
   }

}
