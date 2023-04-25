import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  exports: [
    CommonModule,
    MatSidenavModule,
    MatPaginatorModule,
    MatSelectModule,
  ],
  imports: [
    MatSidenavModule,
  ],
  declarations: []
})
export class MaterialModule { }