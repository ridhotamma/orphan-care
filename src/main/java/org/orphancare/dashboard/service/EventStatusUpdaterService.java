package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.orphancare.dashboard.entity.Event;
import org.orphancare.dashboard.repository.EventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventStatusUpdaterService {

    private final EventRepository eventRepository;

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    @Transactional
    public void updateEventStatuses() {
        LocalDate today = LocalDate.now();

        // Find all events that are not FINISHED or CANCELLED and have end dates in the past
        List<Event> eventsToUpdate = eventRepository.findByStatusNotInAndEndDateBefore(
                List.of(Event.EventStatus.FINISHED, Event.EventStatus.CANCELLED),
                today
        );

        for (Event event : eventsToUpdate) {
            event.setStatus(Event.EventStatus.FINISHED);
            eventRepository.save(event);
            log.info("Updated event status to FINISHED: {}", event.getId());
        }

        // Update ON_PROGRESS status for current events
        List<Event> currentEvents = eventRepository.findByStatusAndStartDateBeforeAndEndDateAfter(
                Event.EventStatus.PENDING,
                today.plusDays(1),
                today.minusDays(1)
        );

        for (Event event : currentEvents) {
            event.setStatus(Event.EventStatus.ON_PROGRESS);
            eventRepository.save(event);
            log.info("Updated event status to ON_PROGRESS: {}", event.getId());
        }
    }
}
