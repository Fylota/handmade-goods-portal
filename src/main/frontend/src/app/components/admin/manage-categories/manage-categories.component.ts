import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { TranslateService } from '@ngx-translate/core';
import { Category, CategoryControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-manage-categories',
  templateUrl: './manage-categories.component.html',
  styleUrls: ['./manage-categories.component.scss']
})
export class ManageCategoriesComponent implements OnInit {
  dataSource: MatTableDataSource<Category> = new MatTableDataSource<Category>();
  columnsToDisplay: string[] = ['id', 'name'];
  selectedCategory: Category | null = null;

  constructor(
    private categoryService: CategoryControllerService,
    private _snackBar: MatSnackBar,
    public translate: TranslateService
  ) { }

  ngOnInit(): void {
    this.refreshData();
  }

  refreshData(): void {
    this.categoryService.getCategories().subscribe(
      (res: Category[]) => this.dataSource.data = res
    )
  }

  handleRowClicked(row: Category) {
    this.selectedCategory = row;
  }

  updateCategory() {
    if (this.selectedCategory !== null) {
      this.categoryService.updateCategory(this.selectedCategory.id!, this.selectedCategory.name! ).subscribe({
        next: () => {
          this._snackBar.open("Category saved", "Dismiss");
          this.selectedCategory = null;
        },
        error: () => this._snackBar.open("Something went wrong :/", "Dismiss")
      });
    }
  }

  cancelEdit() {
    this.selectedCategory = null;
    this.refreshData();
  }

  deleteCategory() {
    if (this.selectedCategory !== null) {
      this.categoryService.deleteCategory(this.selectedCategory.id!).subscribe({
        next: () => {
          this._snackBar.open("Category deleted", "Dismiss");
          this.selectedCategory = null;
          this.refreshData();
        },
        error: (e) => {
          this._snackBar.open("Something went wrong :/", "Dismiss");
          console.error(e);
        }
      });
    }
  }

}
