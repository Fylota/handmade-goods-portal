package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.model_assemblers.PostModelAssembler;
import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.services.IPostService;
import hu.bme.edu.handmade.web.dto.PostDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/posts")
public class PostController {
    private final IPostService postService;
    private final PostModelAssembler assembler;
    PostController(IPostService postService, PostModelAssembler assembler) {
        this.postService = postService;
        this.assembler = assembler;
    }


    @GetMapping()
    public CollectionModel<EntityModel<Post>> getPosts() {
        List<Post> posts = postService.findAllPosts();
        return assembler.toCollectionModel(posts);
    }

    @GetMapping("/{id}")
    public EntityModel<Post> getPostById(@PathVariable("id") long id) {
        Post post = postService.findPostById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id:" + id + " is not found."));
        return assembler.toModel(post);
    }

    @PostMapping()
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto) {
        EntityModel<Post> entityModel = assembler.toModel(postService.uploadPost(postDto));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        Post updatedPost = postService.findPostById(id)
                .map(product -> postService.updatePost(postDto))
                .orElseGet(()->postService.uploadPost(postDto));

        EntityModel<Post> entityModel = assembler.toModel(updatedPost);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        Optional<Post> deletedProduct = postService.findPostById(id);
        deletedProduct.ifPresent(postService::deletePost);
        return ResponseEntity.noContent().build();
    }
}
