package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Event;
import hu.bme.edu.handmade.web.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "id", ignore = true)
    Event toEvent(EventDto eventDto);
}
