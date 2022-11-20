import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';

@NgModule({
  exports: [
    CommonModule,
    MatSidenavModule,
  ],
  imports: [
    MatSidenavModule,
  ],
  declarations: []
})
export class MaterialModule { }