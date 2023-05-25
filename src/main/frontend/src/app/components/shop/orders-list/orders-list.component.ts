import { trigger, state, style, transition, animate } from '@angular/animations';
import { AfterViewInit, Component, Input, OnChanges, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { OrderControllerService, OrderItemDto, UserDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class OrdersListComponent implements OnChanges, AfterViewInit  {
  @Input()
  user!: UserDto;
  myOrders?: OrderItemDto[];

  displayedColumns: string[] = ['id', 'creationDate', 'status', 'action'];
  columnsToDisplayWithExpand = [...this.displayedColumns, 'expand'];
  expandedElement: OrderItemDto | undefined;
  dataSource: MatTableDataSource<OrderItemDto> = new MatTableDataSource();
  @ViewChild(MatSort)
  sort: MatSort = new MatSort;

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
        this.dataSource.sort = this.sort;
      });
    }
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  navigateToDetails(orderId: number): void {
    this.router.navigate(["/orders/view"], {
      queryParams: { id: orderId },
    });
  }
}
