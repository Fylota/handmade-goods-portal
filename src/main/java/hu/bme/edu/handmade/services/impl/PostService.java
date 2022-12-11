package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.PostMapper;
import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.repositories.PostRepository;
import hu.bme.edu.handmade.services.IPostService;
import hu.bme.edu.handmade.web.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService implements IPostService {
    @Autowired
    PostRepository postRepository;
    @Override
    public List<Post> findAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Optional<Post> findPostById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post uploadPost(PostDto postDto) {
        Post post = PostMapper.INSTANCE.toProduct(postDto);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(PostDto postDto) {
        Post post = PostMapper.INSTANCE.toProduct(postDto);
        post.setId(Long.parseLong(postDto.getId()));
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
