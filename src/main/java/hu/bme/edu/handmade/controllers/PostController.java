package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.services.IPostService;
import hu.bme.edu.handmade.web.dto.PostDto;
import hu.bme.edu.handmade.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getPostPages(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "3") int size,
                                                           @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            List<Sort.Order> orders = new ArrayList<>();
            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }
            Pageable paging = PageRequest.of(page, size, Sort.by(orders));
            Page<Post> pagePosts = postService.findPages(paging);
            List<Post> posts = pagePosts.getContent();

            if (posts.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", posts);
            response.put("currentPage", pagePosts.getNumber());
            response.put("totalItems", pagePosts.getTotalElements());
            response.put("totalPages", pagePosts.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") long id) {
        return postService.findPostById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id:" + id + " is not found."));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping()
    public Post addPost(@RequestBody PostDto postDto) {
        return postService.uploadPost(postDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        return postService.findPostById(id)
                .map(product -> postService.updatePost(postDto, id))
                .orElseGet(()->postService.uploadPost(postDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
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

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
