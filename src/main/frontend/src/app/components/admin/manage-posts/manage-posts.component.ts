import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { TranslateService } from '@ngx-translate/core';
import { Post, PostControllerService, PostDto } from 'src/app/core/api/v1';

interface SortValue {
  value: string[];
  viewValue: string;
}

@Component({
  selector: 'app-manage-posts',
  templateUrl: './manage-posts.component.html',
  styleUrls: ['./manage-posts.component.scss']
})
export class ManagePostsComponent implements OnInit {
  length = 10;
  pageSize = 10;
  pageSizeOptions = [5, 10, 25];
  pageIndex = 0;
  sort = ["creationDate", "desc"];
  pageEvent: PageEvent | undefined;
  sortValues: SortValue[] = [
    {value: ["title", "asc"], viewValue: 'Title ascending'},
    {value: ["title", "desc"], viewValue: 'Title descending'},
    {value: ["creationDate", "asc"], viewValue: 'From oldest to newest'},
    {value: ["creationDate", "desc"], viewValue: 'From newest to oldest'},
  ]
  dataSource: MatTableDataSource<Post> = new MatTableDataSource<Post>();
  columnsToDisplay: string[] = ['id', 'title', 'creationDate'];
  selectedPost: Post | null = null;

  constructor(
    private postService: PostControllerService,
    private _snackBar: MatSnackBar,
    public translate: TranslateService
  ) { }

  ngOnInit(): void {
    this.refreshData();
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.refreshData();
  }

  refreshData(): void {
    this.postService.getPostPages(this.pageIndex, this.pageSize, this.sort).subscribe((res: any) => {
      this.length = res["totalItems"];
      this.dataSource.data = res["data"];
    })
  }

  handleRowClicked(row: Post) {
    this.selectedPost = row;
    console.log(row);
  }

  updatePost() {
    if (this.selectedPost !== null) {
      let dto: PostDto = {
        title: this.selectedPost.title,
        content: this.selectedPost.content,
        imageUrl: this.selectedPost.imageUrl
      };
      this.postService.updatePost(this.selectedPost.id!, dto ).subscribe({
        next: () => {
          this._snackBar.open("Post saved", "Dismiss");
          this.selectedPost = null;
        },
        error: () => this._snackBar.open("Something went wrong :/", "Dismiss")
      });
    }
  }

  cancelEdit() {
    this.selectedPost = null;
    this.refreshData();
  }

  deletePost() {
    if (this.selectedPost !== null) {
      this.postService.deletePost(this.selectedPost.id!).subscribe({
        next: () => {
          this._snackBar.open("Post deleted", "Dismiss");
          this.selectedPost = null;
          this.refreshData();
        },
        error: () => this._snackBar.open("Something went wrong :/", "Dismiss")
      });
    }
  }

}
