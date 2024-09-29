package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Document;
import org.orphancare.dashboard.entity.DocumentType;
import org.orphancare.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {
    List<Document> findByOwnerId(UUID ownerId);

    @Query("SELECT DISTINCT d.documentType FROM Document d WHERE d.owner = :owner AND d.documentType.isMandatory = true")
    List<DocumentType> findDistinctMandatoryDocumentTypesByOwner(@Param("owner") User owner);
}
