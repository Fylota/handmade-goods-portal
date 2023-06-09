import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AddressDto,
  UserControllerService,
  UserDto,
} from 'src/app/core/api/v1';

@Component({
  selector: 'app-edit-address-book',
  templateUrl: './edit-address-book.component.html',
  styleUrls: ['./edit-address-book.component.scss'],
})
export class EditAddressBookComponent implements OnInit {
  @Input()
  user!: UserDto;

  @Input()
  address: AddressDto | undefined;

  @Output()
  newCancelEvent = new EventEmitter<boolean>();

  constructor(private httpClientService: UserControllerService) {}
  ngOnInit(): void {
    if (this.address === undefined) {
      this.address = {};
    }
  }

  saveAddress() {
    this.httpClientService
      .addUserAddress(Number(this.user.id), this.address!)
      .subscribe((_data) => {
        alert('User updated successfully.');
        this.cancelEdit();
      });
  }

  cancelEdit() {
    this.newCancelEvent.emit(false);
  }
}
