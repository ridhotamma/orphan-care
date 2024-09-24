package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
