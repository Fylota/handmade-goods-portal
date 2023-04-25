import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  exports: [
    CommonModule,
    MatSidenavModule,
    MatPaginatorModule,
  ],
  imports: [
    MatSidenavModule,
  ],
  declarations: []
})
export class MaterialModule { }