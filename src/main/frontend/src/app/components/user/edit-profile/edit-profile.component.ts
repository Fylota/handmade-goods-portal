import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { UserControllerService, UserDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent {

  @Input()
  user!: UserDto;

  @Output()
  newCancelEvent = new EventEmitter<boolean>();

  constructor(private httpClientService: UserControllerService, private router: Router) { }

  updateUser() {
    this.httpClientService.updateUser(Number(this.user.id), this.user)
    .subscribe(_data => {
      alert("User updated successfully.");
      this.cancelEdit();
    });
  }

  cancelEdit() {
    this.newCancelEvent.emit(false);
  }

}
