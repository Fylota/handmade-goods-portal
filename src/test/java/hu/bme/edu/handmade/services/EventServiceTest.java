package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.mappers.EventMapper;
import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.repositories.EventRepository;
import hu.bme.edu.handmade.services.impl.EventService;
import hu.bme.edu.handmade.web.dto.EventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceTest {
    @InjectMocks
    private EventService eventService;

    @Mock
    EventRepository eventRepository;

    private EventDto eventDto;
    private Event event;

    @BeforeEach
    void setup() {
        eventDto = new EventDto();
        eventDto.setTitle("Test Title");
        eventDto.setDescription("Test description.");
        eventDto.setStartDateTime(LocalDateTime.of(2023,1,24,14,0));
        eventDto.setEndDateTime(LocalDateTime.of(2023,1,24,15,0));

        event = new Event();
        event.setId(1L);
        event.setTitle("Test Title");
        event.setDescription("Test description.");
        event.setStartDateTime(LocalDateTime.of(2023,1,24,14,0));
        event.setEndDateTime(LocalDateTime.of(2023,1,24,15,0));
    }

    @DisplayName("JUnit test for FindAllEvents method")
    @Test
    void testFindAllEvents() {
        // given
        Event event1 = new Event();
        event1.setId(2L);
        event1.setTitle("Test Title 1");
        event1.setDescription("Test description 1.");
        event1.setStartDateTime(LocalDateTime.of(2023,1,25,14,0));
        event1.setEndDateTime(LocalDateTime.of(2023,1,25,15,0));

        given(eventRepository.findAll()).willReturn(List.of(event, event1));

        // when
        List<Event> result = eventService.findAllEvents();

        // then
        assertEquals(2, result.size());
    }

    @DisplayName("JUnit test for FindEventById method")
    @Test
    void testFindEventById() {
        // given
        given(eventRepository.findById(1L)).willReturn(Optional.of(event));

        // when
        Optional<Event> result = eventService.findEventById(event.getId());

        // then
        assertThat(result).isNotEmpty();
    }

    @DisplayName("JUnit test for AddEvent method")
    @Test
    void testAddEvent() {
        // given
        Event newEvent = EventMapper.INSTANCE.toEvent(eventDto);
        given(eventRepository.findById(newEvent.getId())).willReturn(Optional.empty());
        given(eventRepository.save(newEvent)).willReturn(newEvent);

        // when
        Event savedEvent = eventService.addEvent(eventDto);

        // then
        assertThat(savedEvent).isNotNull();
    }

    @DisplayName("JUnit test for updateEvent method")
    @Test
    void testUpdateEvent() {
        // given
        given(eventRepository.save(event)).willReturn(event);
        event.setTitle("Modified Title");
        event.setDescription("Modified description.");

        // when
        Event updatedEvent = eventService.updateEvent(EventMapper.INSTANCE.eventToEventDto(event), 1L);

        // then
        assertThat(updatedEvent.getTitle()).isEqualTo("Modified Title");
        assertThat(updatedEvent.getDescription()).isEqualTo("Modified description.");
    }

    @DisplayName("JUnit test for deleteEvent method")
    @Test
    void testDeleteEvent() {
        // given
        willDoNothing().given(eventRepository).delete(event);

        // when
        eventService.deleteEvent(event);

        // then
        verify(eventRepository, times(1)).delete(event);
    }
}
