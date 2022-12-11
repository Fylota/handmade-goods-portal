package hu.bme.edu.handmade.model_assemblers;

import hu.bme.edu.handmade.controllers.PostController;
import hu.bme.edu.handmade.models.Post;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {
    @Override
    public EntityModel<Post> toModel(Post entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PostController.class).getPostById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).getPosts()).withRel("posts"));
    }

    @Override
    public CollectionModel<EntityModel<Post>> toCollectionModel(Iterable<? extends Post> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
