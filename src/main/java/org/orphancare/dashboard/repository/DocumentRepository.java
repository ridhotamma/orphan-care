package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByOwnerId(UUID ownerId);
}
