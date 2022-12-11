import { Component } from '@angular/core';
import { PostService, Post } from 'src/app/service/post.service';

@Component({
  selector: 'app-upload-news',
  templateUrl: './upload-news.component.html',
  styleUrls: ['./upload-news.component.scss']
})
export class UploadNewsComponent {

  post: Post = new Post("", "", "", new Date())
  constructor(private postService: PostService) { }

  createPost() {
    this.post.creationDate = new Date();
    this.postService.createPost(this.post)
    .subscribe(() => {
      console.log(this.post);
      alert("Post created successfully.");
    });
  }

}
