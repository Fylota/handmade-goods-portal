import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  Category,
  CategoryControllerService,
  Post,
  PostControllerService,
  PostDto,
  Product,
  ProductControllerService,
} from 'src/app/core/api/v1';


@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  topCategories?: Category[];
  newProducts?: Product[];
  newPosts?: Post[];

  constructor(
    private categoryService: CategoryControllerService,
    private productService: ProductControllerService,
    private postService: PostControllerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe((res) => {
      this.topCategories = res.slice(0, 3);
      console.log(this.topCategories);
    });
    this.productService.getProducts(0, 4, ["name", "asc"]).subscribe((res: any) => {
      this.newProducts = res["products"];
      console.log(this.newProducts);
    });
    this.postService.getPostPages(0,3, ["creationDate", "desc"]).subscribe((res: any) => {
      this.newPosts = res["data"];
      console.log(this.newPosts);
    });
  }

  navigateToProductDetails(product: Product) {
    this.router.navigate(['products/view'], {
      queryParams: { id: product.id },
    });
  }

  goToCategory(cat: Category) {
    const catName = cat ? cat.name : null;
    this.router.navigate(['products/category'], { queryParams: { name: catName }})
  }

  navigateToPost(post: Post) {
    this.router.navigate(['posts/view'], {
      queryParams: {id: post.id},
    });
  }
}
