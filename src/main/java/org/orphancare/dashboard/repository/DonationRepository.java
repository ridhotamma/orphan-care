package org.orphancare.dashboard.repository;

import org.orphancare.dashboard.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface DonationRepository extends JpaRepository<Donation, UUID> {
    Page<Donation> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT dt.name as \"name\", COUNT(d.id) as amount " +
            "FROM donation_types dt " +
            "LEFT JOIN donations d ON dt.id = d.donation_type_id " +
            "GROUP BY dt.name",
            nativeQuery = true)
    List<Map<String, Object>> getDonationTypeDistribution();

    @Query(value = "SELECT dt.name as \"name\", " +
            "SUM(d.amount) as amount, " +
            "u.name as unit " +
            "FROM donation_types dt " +
            "LEFT JOIN donations d ON dt.id = d.donation_type_id " +
            "LEFT JOIN units u ON u.id = d.unit_id " +
            "GROUP BY dt.name, u.name",
            nativeQuery = true)
    List<Map<String, Object>> getTotalDonationAmountByType();

    @Query(value = "SELECT dt.name as \"donationName\", " +
            "CONCAT(CAST(SUM(d.amount) AS VARCHAR), ' ', u.name) as amount, " +
            "d.donator_name as donator " +
            "FROM donations d " +
            "JOIN donation_types dt ON dt.id = d.donation_type_id " +
            "JOIN units u ON u.id = d.unit_id " +
            "GROUP BY dt.name, u.name, d.donator_name " +
            "ORDER BY SUM(d.amount) DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Map<String, Object>> findTop5DonorsWithTypeAndUnit();

    @Query(value = "SELECT dt.name as \"donationName\", " +
            "CONCAT(CAST(SUM(d.amount) AS VARCHAR), ' ', u.name) as amount, " +
            "TO_CHAR(d.received_date, 'YYYY-MM') as month " +
            "FROM donations d " +
            "JOIN donation_types dt ON dt.id = d.donation_type_id " +
            "JOIN units u ON u.id = d.unit_id " +
            "WHERE d.received_date BETWEEN :startDate AND :endDate " +
            "GROUP BY dt.name, u.name, TO_CHAR(d.received_date, 'YYYY-MM') " +
            "ORDER BY dt.name, TO_CHAR(d.received_date, 'YYYY-MM')",
            nativeQuery = true)
    List<Map<String, Object>> getDonationTrendsByMonthWithTypeAndUnit(LocalDate startDate, LocalDate endDate);
}
