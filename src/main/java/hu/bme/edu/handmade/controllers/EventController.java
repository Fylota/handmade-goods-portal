package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.model_assemblers.EventModelAssembler;
import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.services.IEventService;
import hu.bme.edu.handmade.web.dto.EventDto;
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
@RequestMapping("/events")
public class EventController {
    private final IEventService eventService;
    private final EventModelAssembler assembler;
    EventController(IEventService eventService, EventModelAssembler assembler) {
        this.eventService = eventService;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Event>> getEvents() {
        List<Event> events = eventService.findAllEvents();
        return assembler.toCollectionModel(events);
    }

    @GetMapping("/{id}")
    public EntityModel<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.findEventById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id:" + id + " is not found."));
        return assembler.toModel(event);
    }

    @PostMapping()
    public ResponseEntity<?> addEvent(@RequestBody EventDto dto) {
        EntityModel<Event> entityModel = assembler.toModel(eventService.addEvent(dto));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id, @RequestBody EventDto eventDto) {
        Event updatedEvent = eventService.findEventById(id)
                .map(event -> eventService.updateEvent(eventDto, id))
                .orElseGet(()->eventService.addEvent(eventDto));

        EntityModel<Event> entityModel = assembler.toModel(updatedEvent);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        Optional<Event> deletedEvent = eventService.findEventById(id);
        deletedEvent.ifPresent(eventService::deleteEvent);
        return ResponseEntity.noContent().build();
    }
}
