import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Post, PostControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss'],
})
export class PostsComponent {
  posts$ = this.postService.getPosts();
  constructor(
    private postService: PostControllerService,
    private router: Router
  ) {}

  navigateToPostDetails(post: Post): void {
    this.router.navigate(['posts/view'], {
      queryParams: { id: post.id },
    });
  }
}
