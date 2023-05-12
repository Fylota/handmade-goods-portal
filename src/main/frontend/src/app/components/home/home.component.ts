import { Component, OnInit } from '@angular/core';
import {
  Category,
  CategoryControllerService,
  PostControllerService,
  PostDto,
  ProductControllerService,
  ProductDto,
} from 'src/app/core/api/v1';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  topCategories?: Category[];
  topProducts?: ProductDto[];
  newPosts?: PostDto[];
  constructor(
    private categoryService: CategoryControllerService,
    private productService: ProductControllerService,
    private postService: PostControllerService
  ) {}

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe((res) => {
      console.log(res);

    });
    this.productService.getProducts(0, 4, ["name", "asc"]).subscribe((res) => console.log(res));
    this.postService.getPosts().subscribe((res) => console.log(res));
  }
}
