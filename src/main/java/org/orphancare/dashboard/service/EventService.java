package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.EventDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.entity.Event;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.mapper.EventMapper;
import org.orphancare.dashboard.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public PaginatedResponse<List<EventDto>> getAllEvents(String name, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Event> eventsPage = (name == null || name.isEmpty()) ?
                eventRepository.findAll(pageable) :
                eventRepository.findByNameContainingIgnoreCase(name, pageable);

        List<EventDto> eventDtos = eventsPage.getContent()
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());

        PaginatedResponse.Meta meta = new PaginatedResponse.Meta(
                eventsPage.getNumber(),
                eventsPage.getSize(),
                eventsPage.getTotalElements(),
                eventsPage.getTotalPages()
        );

        return new PaginatedResponse<>(eventDtos, meta);
    }

    public EventDto getEventById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return eventMapper.toDto(event);
    }

    public EventDto createEvent(EventDto eventDto) {
        Event event = eventMapper.toEntity(eventDto);
        return eventMapper.toDto(eventRepository.save(event));
    }

    public EventDto updateEvent(UUID id, EventDto eventDto) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        existingEvent.setName(eventDto.getName());
        existingEvent.setStartDate(eventDto.getStartDate());
        existingEvent.setEndDate(eventDto.getEndDate());
        existingEvent.setOrganizer(eventDto.getOrganizer());
        existingEvent.setStatus(eventDto.getStatus());
        existingEvent.setOrganizerPhoneNumber(eventDto.getOrganizerPhoneNumber());
        existingEvent.setPlace(eventDto.getPlace());

        return eventMapper.toDto(eventRepository.save(existingEvent));
    }

    public void deleteEventById(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
}