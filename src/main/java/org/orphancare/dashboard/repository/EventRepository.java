package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("SELECT e FROM Event e WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.organizer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.organizerPhoneNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.place) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:status IS NULL OR e.status = :status)")
    Page<Event> findBySearchCriteriaAndStatus(
            @Param("search") String search,
            @Param("status") Event.EventStatus status,
            Pageable pageable);

    @Query(value = "SELECT e.id as id, e.name as name, e.start_date as \"startDate\", " +
            "e.end_date as \"endDate\", e.organizer as organizer, e.status as status, " +
            "e.organizer_phone_number as \"organizerPhoneNumber\", e.place as place " +
            "FROM events e ORDER BY e.start_date DESC LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> findLatest10Events();
}
