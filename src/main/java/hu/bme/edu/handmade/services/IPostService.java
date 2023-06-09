package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.web.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<Post> findAllPosts();
    Optional<Post> findPostById(long id);
    Post uploadPost(PostDto postDto);
    Post updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
    Page<Post> findPages(Pageable paging);
}
