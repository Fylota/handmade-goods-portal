import { Component, OnInit } from '@angular/core';
import { HttpPostService, Post } from 'src/app/service/http-post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  posts: Post[] = []
  constructor(private postService: HttpPostService) { }

  ngOnInit(): void {
    this.postService.getPosts().subscribe(
      response => this.posts = response
    );
  }

}
