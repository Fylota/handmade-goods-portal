package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.services.IEventService;
import hu.bme.edu.handmade.web.dto.EventDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/events")
public class EventController {
    private final IEventService eventService;
    EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<Event> getEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.findEventById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id:" + id + " is not found."));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public Event addEvent(@RequestBody EventDto dto) {
        return eventService.addEvent(dto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable("id") Long id, @RequestBody EventDto eventDto) {
        return eventService.findEventById(id)
                .map(event -> eventService.updateEvent(eventDto, id))
                .orElseGet(()->eventService.addEvent(eventDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
