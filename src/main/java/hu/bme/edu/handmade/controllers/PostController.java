package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.services.IPostService;
import hu.bme.edu.handmade.web.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/post")
public class PostController {
    @Autowired
    IPostService postService;

    @GetMapping()
    public List<Post> getPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable("id") long id) {
        return postService.findPostById(id);
    }

    @PostMapping()
    public void addPost(@RequestBody PostDto postDto) {
        postService.uploadPost(postDto);
    }

}
