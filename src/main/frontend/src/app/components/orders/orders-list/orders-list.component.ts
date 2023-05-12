import { Component, Input, OnChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { OrderControllerService, OrderItemDto, UserDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.scss']
})
export class OrdersListComponent implements OnChanges  {
  @Input()
  user!: UserDto;
  myOrders?: OrderItemDto[];

  displayedColumns: string[] = ['id', 'creationDate', 'status', 'action'];
  dataSource?: MatTableDataSource<OrderItemDto>;

  constructor(
    private orderService: OrderControllerService,
    private router: Router
  ) { }

  ngOnChanges(): void {
    if (this.user.id != undefined) {
      this.orderService.getOrdersByUserId(Number(this.user.id)).subscribe(orders => {
        console.log(this.user);
        console.log(orders);
        this.myOrders = orders;
        this.dataSource = new MatTableDataSource(this.myOrders);
      });
    }
  }

  navigateToDetails(orderId: number): void {
    this.router.navigate(["/orders/view"], {
      queryParams: { id: orderId },
    });
  }
}
