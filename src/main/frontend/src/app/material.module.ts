import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  exports: [
    CommonModule,
    MatSidenavModule,
    MatPaginatorModule,
    MatSelectModule,
    MatSnackBarModule,
  ],
  imports: [
    MatSidenavModule,
  ],
  declarations: []
})
export class MaterialModule { }