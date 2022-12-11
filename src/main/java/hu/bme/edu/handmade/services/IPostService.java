package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.web.dto.PostDto;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<Post> findAllPosts();
    Optional<Post> findPostById(long id);
    Post uploadPost(PostDto postDto);
    Post updatePost(PostDto postDto);
    void deletePost(Post post);
}
