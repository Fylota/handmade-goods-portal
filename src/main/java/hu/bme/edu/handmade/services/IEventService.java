package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.web.dto.EventDto;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<Event> findAllEvents();
    Optional<Event> findEventById(Long id);
    Event addEvent(EventDto eventDto);
}
