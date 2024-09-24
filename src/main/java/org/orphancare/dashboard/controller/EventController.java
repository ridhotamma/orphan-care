package org.orphancare.dashboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.EventDto;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PaginatedResponse<List<EventDto>>> getEvents(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {
        PaginatedResponse<List<EventDto>> events = eventService.getAllEvents(name, page, perPage);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> getEventById(@PathVariable UUID id) {
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid EventDto eventDto) {
        EventDto createdEvent = eventService.createEvent(eventDto);
        return ResponseEntity.status(201).body(createdEvent);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDto> updateEvent(@PathVariable UUID id, @RequestBody @Valid EventDto eventDto) {
        EventDto updatedEvent = eventService.updateEvent(id, eventDto);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }
}