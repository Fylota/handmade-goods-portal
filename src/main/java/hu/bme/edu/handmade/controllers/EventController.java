package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.services.IEventService;
import hu.bme.edu.handmade.web.dto.EventDto;
import hu.bme.edu.handmade.web.dto.error.ResourceNotFoundException;
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

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getEventPages(@RequestParam(defaultValue = "0") int page,
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
            Page<Event> pageEvents = eventService.findPages(paging);
            List<Event> events = pageEvents.getContent();

            if (events.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", events);
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.findEventById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id:" + id + " is not found."));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PostMapping()
    public Event addEvent(@RequestBody EventDto dto) {
        return eventService.addEvent(dto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable("id") Long id, @RequestBody EventDto eventDto) {
        return eventService.findEventById(id)
                .map(event -> eventService.updateEvent(eventDto, id))
                .orElseGet(()->eventService.addEvent(eventDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer_Authentication")
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

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
