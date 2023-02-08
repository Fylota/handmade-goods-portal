package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.EventMapper;
import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.repositories.EventRepository;
import hu.bme.edu.handmade.services.IEventService;
import hu.bme.edu.handmade.web.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService implements IEventService {
    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> findAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event addEvent(EventDto eventDto) {
        Event event = EventMapper.INSTANCE.toEvent(eventDto);
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(EventDto eventDto, Long eventId) {
        Event event = EventMapper.INSTANCE.toEvent(eventDto);
        event.setId(eventId);
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

}
