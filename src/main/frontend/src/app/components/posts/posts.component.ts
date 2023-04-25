import { Component } from '@angular/core';
import { PostControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent {
  posts$ = this.postService.getPosts();
  constructor(private postService: PostControllerService) { }

}
