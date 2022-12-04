import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class Post {
  constructor(
    public id: string,
    public title: string,
    public content: string,
    public creationDate: Date
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class HttpPostService {

  constructor(private httpClient: HttpClient) { }

  createPost(post: Post) {
    return this.httpClient.post<Post>("http://localhost:8080/post", post);
  }

  getPosts() {
    return this.httpClient.get<Post[]>('http://localhost:8080/post');
  }
}
