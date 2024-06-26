package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_ADMIN'")
    long countUsersByRoleAdmin();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_USER'")
    long countUsersByRoleUser();
}
