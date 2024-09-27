package org.orphancare.dashboard.repository;

import io.micrometer.common.lang.NonNullApi;
import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.RoleType;
import org.orphancare.dashboard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@NonNullApi
public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN u.profile p LEFT JOIN u.roles r WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.fullName) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:gender IS NULL OR p.gender = :gender) AND " +
            "(:roles IS NULL OR r IN :roles) AND " +
            "(:isAlumni IS NULL OR p.isAlumni = :isAlumni) AND " +
            "(:isCareTaker IS NULL OR p.isCareTaker = :isCareTaker) AND " +
            "(:active IS NULL OR u.active = :active)")
    Page<User> findBySearchCriteriaAndRoles(
            @Param("search") String search,
            @Param("gender") Gender gender,
            @Param("roles") List<RoleType> roles,
            @Param("isAlumni") Boolean isAlumni,
            @Param("isCareTaker") Boolean isCareTaker,
            @Param("active") Boolean active,
            Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_ADMIN'")
    long countUsersByRoleAdmin();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_USER'")
    long countUsersByRoleUser();

    @Query("SELECT COUNT(u) FROM User u JOIN u.profile p WHERE p.isCareTaker = true")
    long countCaretakers();

    @Query("SELECT COUNT(u) FROM User u JOIN u.profile p WHERE p.isCareTaker = false AND p.isAlumni = false")
    long countStudents();
}
