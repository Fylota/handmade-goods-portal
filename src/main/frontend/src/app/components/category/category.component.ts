import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'src/app/models/category.model';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  category: Category = new Category("","");
  categoryName: string = "";

  constructor(private route: ActivatedRoute, private categoryService: CategoryService) { }

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
