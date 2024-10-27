package org.orphancare.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.orphancare.dashboard.dto.EventDto;
import org.orphancare.dashboard.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "statusText", expression = "java(event.getStatusText())")
    EventDto toDto(Event event);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Event toEntity(EventDto dto);
}
