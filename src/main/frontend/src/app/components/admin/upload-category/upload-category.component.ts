import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Category, CategoryControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-upload-category',
  templateUrl: './upload-category.component.html',
  styleUrls: ['./upload-category.component.scss']
})
export class UploadCategoryComponent {
  category: Category = {};

  constructor(
    private categoryService: CategoryControllerService,
    private _snackBar: MatSnackBar
  ) { }

  createCategory() {
    this.categoryService.addNewCategory(this.category).subscribe(() => {
      this._snackBar.open("Product saved", "Dismiss");
      this.category = {};
    })
  }

}
