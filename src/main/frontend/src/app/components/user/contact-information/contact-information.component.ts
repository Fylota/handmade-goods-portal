import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-contact-information',
  templateUrl: './contact-information.component.html',
  styleUrls: ['./contact-information.component.scss']
})
export class ContactInformationComponent {
  @Input()
  user!: UserDto;

  @Output() newEditEvent = new EventEmitter<boolean>();

  editUser() {
    console.log("edit clicked");
    this.newEditEvent.emit(true)
  }

}
