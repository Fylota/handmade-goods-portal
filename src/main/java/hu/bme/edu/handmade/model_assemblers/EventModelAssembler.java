package hu.bme.edu.handmade.model_assemblers;

import hu.bme.edu.handmade.controllers.EventController;
import hu.bme.edu.handmade.models.Event;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventModelAssembler implements RepresentationModelAssembler<Event, EntityModel<Event>> {
    @Override
    public EntityModel<Event> toModel(Event event) {
        return EntityModel.of(event,
                linkTo(methodOn(EventController.class).getEventById(event.getId())).withSelfRel(),
                linkTo(methodOn(EventController.class).getEvents()).withRel("events"));
    }

    @Override
    public CollectionModel<EntityModel<Event>> toCollectionModel(Iterable<? extends Event> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
