import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Category, HttpProductService } from 'src/app/service/http-product.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  category: Category = new Category("","");
  categoryName: string = "";

  constructor(private route: ActivatedRoute, private productService: HttpProductService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.categoryName = params['name'];
    });
    if (this.categoryName !== "") {
      this.productService.getCategoryByName(this.categoryName).subscribe(
        result => this.category = result);
    }
  }
}
