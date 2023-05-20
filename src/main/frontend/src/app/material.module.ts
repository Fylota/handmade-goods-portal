import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';

@NgModule({
  exports: [
    CommonModule,
    MatPaginatorModule,
    MatSelectModule,
    MatSnackBarModule,
    MatSlideToggleModule,
    MatTableModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    MatRadioModule
  ],
  imports: [
  ],
  declarations: []
})
export class MaterialModule { }