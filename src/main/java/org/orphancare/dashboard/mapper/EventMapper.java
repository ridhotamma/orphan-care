package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.orphancare.dashboard.dto.EventDto;
import org.orphancare.dashboard.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(Event event);
    Event toEntity(EventDto eventDto);
}