package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.services.IPostService;
import hu.bme.edu.handmade.web.dto.PostDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/posts")
public class PostController {
    private final IPostService postService;
    PostController(IPostService postService) {
        this.postService = postService;
    }


    @GetMapping()
    public List<Post> getPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") long id) {
        return postService.findPostById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id:" + id + " is not found."));
    }

    @PostMapping()
    public Post addPost(@RequestBody PostDto postDto) {
        return postService.uploadPost(postDto);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        return postService.findPostById(id)
                .map(product -> postService.updatePost(postDto, id))
                .orElseGet(()->postService.uploadPost(postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
