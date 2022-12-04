import { Component } from '@angular/core';
import { HttpPostService, Post } from 'src/app/service/http-post.service';

@Component({
  selector: 'app-upload-news',
  templateUrl: './upload-news.component.html',
  styleUrls: ['./upload-news.component.scss']
})
export class UploadNewsComponent {

  post: Post = new Post("", "", "", new Date())
  constructor(private postService: HttpPostService) { }

  createPost() {
    this.post.creationDate = new Date();
    this.postService.createPost(this.post)
    .subscribe(data => {
      console.log(this.post);
      alert("Post created successfully.");
    });
  }

}
