import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../models/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }

  createPost(post: Post) {
    return this.httpClient.post<Post>("http://localhost:8080/posts", post);
  }

  getPosts() {
    return this.httpClient.get<Post[]>('http://localhost:8080/posts');
  }

  getPost(postId: string) {
    return this.httpClient.get<Post>('http://localhost:8080/posts' + "/" + postId);
  }

  updatePost(post: Post) {
    return this.httpClient.put<Post>('http://localhost:8080/posts' + "/" + post.id, post);
  }

  deletePost(post: Post) {
    return this.httpClient.delete<Post>("http://localhost:8080/posts" + "/" + post.id);
  }
}
export { Post };
