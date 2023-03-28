package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.EventMapper;
import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.repositories.EventRepository;
import hu.bme.edu.handmade.services.IEventService;
import hu.bme.edu.handmade.web.dto.EventDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService implements IEventService {
    private final EventRepository eventRepository;

    EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

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
        Event foundEvent = eventRepository.findById(eventId).orElseThrow();
        EventMapper.INSTANCE.updateEventFromDto(eventDto, foundEvent);
        return eventRepository.save(foundEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

}
