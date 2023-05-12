import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AddressDto, UserDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-address-book',
  templateUrl: './address-book.component.html',
  styleUrls: ['./address-book.component.scss'],
})
export class AddressBookComponent {
  @Input()
  user!: UserDto;

  @Output()
  newEditEvent = new EventEmitter<boolean>();

  @Output()
  selectedAddressEvent = new EventEmitter<AddressDto>();

  editAddress(address?: AddressDto) {
    this.newEditEvent.emit(true);
    this.selectedAddressEvent.emit(address);
  }
}
