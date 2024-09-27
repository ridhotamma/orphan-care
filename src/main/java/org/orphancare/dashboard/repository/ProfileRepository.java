package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByUserId(UUID userId);
    long countByGender(Gender gender);
    long countByGenderAndJoinDateBetween(Gender gender, LocalDate startDate, LocalDate endDate);
}
