import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { PostControllerService, PostDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-upload-news',
  templateUrl: './upload-news.component.html',
  styleUrls: ['./upload-news.component.scss']
})
export class UploadNewsComponent {

  post: PostDto = {title: "", content: ""};
  constructor(
    private postService: PostControllerService,
    private _snackBar: MatSnackBar,
    public translate: TranslateService
  ) { }

  createPost() {
    this.postService.addPost(this.post)
    .subscribe(() => {
      console.log(this.post);
      this._snackBar.open("Product saved", "Dismiss");
      this.post = {};
    });
  }

}
