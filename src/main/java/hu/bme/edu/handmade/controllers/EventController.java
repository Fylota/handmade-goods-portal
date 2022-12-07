package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.services.IEventService;
import hu.bme.edu.handmade.web.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/event")
public class EventController {
    @Autowired
    IEventService eventService;

    @GetMapping()
    public List<Event> getEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Long id) {
        return eventService.findEventById(id);
    }

    @PostMapping()
    public void addEvent(@RequestBody EventDto dto) {
        eventService.addEvent(dto);
    }
}
