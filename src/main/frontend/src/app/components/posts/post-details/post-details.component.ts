import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post, PostControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss']
})
export class PostDetailsComponent {
  param = '';
  post?: Post;

  constructor(
    private route: ActivatedRoute,
    private postService: PostControllerService,
  ) {
    this.route.queryParams.subscribe((params) => {
      this.param = params['id'];
      this.postService.getPostById(Number(this.param)).subscribe((res) => {
        this.post = res;
        console.log(res);
      });
    });
   }
}
