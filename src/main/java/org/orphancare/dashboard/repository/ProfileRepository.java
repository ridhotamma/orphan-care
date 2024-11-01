package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUser(User user);

    Optional<Profile> findByUserId(UUID userId);

    boolean existsByKkNumber(String KkNumber);

    @Query("SELECT COUNT(p) FROM Profile p JOIN p.user u WHERE p.gender = :gender AND p.isCareTaker = false AND p.isAlumni = false")
    long countStudentsByGender(Gender gender);

    @Query("SELECT COUNT(p) FROM Profile p JOIN p.user u WHERE p.gender = :gender AND p.isCareTaker = false AND p.isAlumni = false AND p.joinDate BETWEEN :startDate AND :endDate")
    long countStudentsByGenderAndJoinDateBetween(Gender gender, LocalDate startDate, LocalDate endDate);
}
