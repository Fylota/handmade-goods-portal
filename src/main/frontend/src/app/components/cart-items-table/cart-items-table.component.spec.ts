import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartItemsTableComponent } from './cart-items-table.component';

describe('CartItemsTableComponent', () => {
  let component: CartItemsTableComponent;
  let fixture: ComponentFixture<CartItemsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CartItemsTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CartItemsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
