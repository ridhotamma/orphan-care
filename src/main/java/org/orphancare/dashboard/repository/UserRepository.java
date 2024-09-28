package org.orphancare.dashboard.repository;

import io.micrometer.common.lang.NonNullApi;
import org.orphancare.dashboard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@NonNullApi
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_ADMIN'")
    long countUsersByRoleAdmin();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_USER'")
    long countUsersByRoleUser();

    @Query("SELECT COUNT(u) FROM User u JOIN u.profile p WHERE p.isCareTaker = true")
    long countCaretakers();

    @Query("SELECT COUNT(u) FROM User u JOIN u.profile p WHERE p.isCareTaker = false AND p.isAlumni = false")
    long countStudents();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r JOIN u.profile p WHERE r = 'ROLE_ADMIN' AND p.isCareTaker = true")
    long countAdminCaretakers();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r JOIN u.profile p WHERE r = 'ROLE_ADMIN' AND p.isCareTaker = false")
    long countAdminNonCaretakers();

    @Query("SELECT COUNT(u) FROM User u JOIN u.profile p WHERE p.isAlumni = true")
    long countAlumni();
}
