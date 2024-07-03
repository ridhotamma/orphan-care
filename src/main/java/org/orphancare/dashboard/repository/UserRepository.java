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

    @Query("SELECT u FROM User u LEFT JOIN u.profile p LEFT JOIN u.roles r WHERE " +
            "(:search IS NULL OR :search = '' OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.fullName) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:gender IS NULL OR p.gender = :gender) AND " +
            "(:roles IS NULL OR r IN :roles)")
    Page<User> findBySearchGenderAndRoles(@Param("search") String search,
                                          @Param("gender") Gender gender,
                                          @Param("roles") List<RoleType> roles,
                                          Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_ADMIN'")
    long countUsersByRoleAdmin();

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = 'ROLE_USER'")
    long countUsersByRoleUser();
}
