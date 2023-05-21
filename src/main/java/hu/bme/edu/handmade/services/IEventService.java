package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.web.dto.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<Event> findAllEvents();
    Optional<Event> findEventById(Long id);
    Event addEvent(EventDto eventDto);
    Event updateEvent(EventDto eventDto, Long id);
    void deleteEvent(Long eventId);
    Page<Event> findPages(Pageable paging);
}
