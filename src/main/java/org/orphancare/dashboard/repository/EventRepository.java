package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {

    @Query(value = "SELECT e.id as id, e.name as name, e.start_date as \"startDate\", " +
            "e.end_date as \"endDate\", e.organizer as organizer, e.status as status, " +
            "e.organizer_phone_number as \"organizerPhoneNumber\", e.place as place " +
            "FROM events e ORDER BY e.start_date DESC LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> findLatest10Events();
}