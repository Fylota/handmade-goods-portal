import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { UserDto, UserControllerService, RoleDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ManageUsersComponent implements OnInit {
  dataSource: MatTableDataSource<UserDto> = new MatTableDataSource<UserDto>();
  columnsToDisplay: string[] = ['id', 'firstName', 'lastName', 'email', 'phoneNumber', 'subscribedToNewsletter'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement: UserDto | undefined;
  statusSelectValue = "";
  roles: RoleDto[] | undefined;
  currentRoles: RoleDto[] = [];
  isAdmin = false;

  constructor(
    private userService: UserControllerService
  ) { }

  ngOnInit(): void {
    this.refreshUsersList();
    this.userService.getRoles().subscribe(res => {
      this.roles = res;
    })
  }

  refreshUsersList(): void {
    this.userService.getUsers().subscribe(result => {
      this.dataSource.data = result;
    })
  }

  deleteUser():void {}

  getUserRoles(userId: number) {
    this.userService.getUserRoles(userId).subscribe(res => {
      this.currentRoles = res;
      this.isAdmin = this.currentRoles.filter(e => e.name === 'ROLE_ADMIN').length > 0;
    });
  }
  
  setAsAdmin(userId: number) {
    if (this.roles !== undefined) {
      let userAdminIds = this.roles.map(r => r.id!);
      this.userService.setRole(userId, userAdminIds).subscribe(_ => this.getUserRoles(userId));
    }
  }

  unsetAsAdmin(userId: number) {
    if (this.roles !== undefined) {
      let userRoleId = [this.roles.find(r => r.name === 'ROLE_USER')!.id!];
      this.userService.setRole(userId, userRoleId).subscribe(_ => this.getUserRoles(userId));
    }
  }
}
