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
            "CONCAT(TO_CHAR(TRUNC(SUM(d.amount)), 'FM999,999,999'), ' ', u.name) as amount " +
            "FROM donation_types dt " +
            "LEFT JOIN donations d ON dt.id = d.donation_type_id " +
            "LEFT JOIN units u ON u.id = d.unit_id " +
            "GROUP BY dt.name, u.name",
            nativeQuery = true)
    List<Map<String, Object>> getTotalDonationAmountByType();

    @Query(value = "SELECT dt.name as \"donationName\", " +
            "CONCAT(TO_CHAR(TRUNC(SUM(d.amount)), 'FM999,999,999'), ' ', u.name) as amount, " +
            "d.donator_name as donator " +
            "FROM donations d " +
            "JOIN donation_types dt ON dt.id = d.donation_type_id " +
            "JOIN units u ON u.id = d.unit_id " +
            "GROUP BY dt.name, u.name, d.donator_name " +
            "ORDER BY SUM(d.amount) DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Map<String, Object>> findTop5DonorsWithTypeAndUnit();

    @Query(value = """
            SELECT
                TRIM(TO_CHAR(d.received_date, 'Month')) as "month",
                dt.name as "donationTypeName",
                COUNT(d.id) as "totalDistribution"
            FROM donations d
            JOIN donation_types dt ON dt.id = d.donation_type_id
            WHERE d.received_date BETWEEN :startDate AND :endDate
            GROUP BY TRIM(TO_CHAR(d.received_date, 'Month')), dt.name
            ORDER BY MIN(d.received_date)
""", nativeQuery = true)
    List<Map<String, Object>> getDonationTrendsByMonthWithTypeAndUnit(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT d.id, d.donator_name as \"donatorName\", dt.name as \"donationType\", " +
            "CONCAT(TO_CHAR(TRUNC(d.amount), 'FM999,999,999'), ' ', u.name) as amount, " +
            "d.received_date as \"receivedDate\" " +
            "FROM donations d " +
            "JOIN donation_types dt ON dt.id = d.donation_type_id " +
            "JOIN units u ON u.id = d.unit_id " +
            "ORDER BY d.received_date DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Map<String, Object>> findLatest10Donations();
}
