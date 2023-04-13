import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoryControllerService } from 'src/app/core/api/v1';
import { Category } from 'src/app/core/api/v1/model/category';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  category?: Category;
  categoryName: string = "";

  constructor(private route: ActivatedRoute, private categoryService: CategoryControllerService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.categoryName = params['name'];
    });
    if (this.categoryName !== "") {
      this.categoryService.getCategoryByName(this.categoryName).subscribe(
        result => this.category = result);
    }
  }
}
