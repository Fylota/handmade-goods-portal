import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { OrderControllerService, OrderItemDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-manage-orders',
  templateUrl: './manage-orders.component.html',
  styleUrls: ['./manage-orders.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ManageOrdersComponent implements OnInit {
  orders: OrderItemDto[] = [];
  dataSource: MatTableDataSource<OrderItemDto> = new MatTableDataSource<OrderItemDto>();
  columnsToDisplay: string[] = ['id', 'userId', 'creationDate', 'status', 'paymentMethod', 'shippingMethod'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement: OrderItemDto | undefined;
  statusSelectValue = "";

  constructor(
    private orderService: OrderControllerService
  ) { }

  ngOnInit(): void {
    this.refreshOrders();
  }

  changeOrderStatus(orderId: number):void {
    this.orderService.updateOrderStatus(orderId, this.statusSelectValue).subscribe(res => {
      console.log(res);
      this.refreshOrders();
    });
  }

  refreshOrders(): void {
    this.orderService.getOrders().subscribe(result => {
      this.orders = result;
      this.dataSource.data = result;
    })
  }
}
