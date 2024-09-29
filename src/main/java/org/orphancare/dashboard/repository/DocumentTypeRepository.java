package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, UUID> {
    List<DocumentType> findByIsMandatoryTrue();
}
