import { Component } from '@angular/core';
import { PostControllerService, PostDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-upload-news',
  templateUrl: './upload-news.component.html',
  styleUrls: ['./upload-news.component.scss']
})
export class UploadNewsComponent {

  post: PostDto = {title: "", content: ""};
  constructor(private postService: PostControllerService) { }

  createPost() {
    this.postService.addPost(this.post)
    .subscribe(() => {
      console.log(this.post);
      alert("Post created successfully.");
    });
  }

}
