package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalyticService {

    private final UserRepository userRepository;
    private final BedRoomRepository bedRoomRepository;
    private final InventoryRepository inventoryRepository;
    private final DonationRepository donationRepository;

    public Map<String, Object> getHomePageAnalytic() {
        Map<String, Object> analytics = new HashMap<>();

        long adminCount = userRepository.countUsersByRoleAdmin();
        long userCount = userRepository.countUsersByRoleUser();
        long inventoryCount = inventoryRepository.count();
        long bedRoomCount = bedRoomRepository.count();

        analytics.put("adminCount", adminCount);
        analytics.put("userCount", userCount);
        analytics.put("inventoryCount", inventoryCount);
        analytics.put("bedRoomCount", bedRoomCount);

        return analytics;
    }

    public Map<String, Object> getDashboardAnalytics() {
        Map<String, Object> dashboard = new HashMap<>();

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(5).withDayOfMonth(1);

        List<Map<String, Object>> donationTrends = donationRepository.getDonationTrendsByMonthWithTypeAndUnit(startDate, endDate);
        dashboard.put("donationTrends", donationTrends);

        List<Map<String, Object>> donationTypeDistribution = donationRepository.getDonationTypeDistribution();
        dashboard.put("donationTypeDistribution", donationTypeDistribution);

        List<Map<String, Object>> totalDonationAmount = donationRepository.getTotalDonationAmountByType();
        dashboard.put("totalDonationAmount", totalDonationAmount);

        List<Map<String, Object>> topDonors = donationRepository.findTop5DonorsWithTypeAndUnit();
        dashboard.put("topDonors", topDonors);

        List<Map<String, Object>> latest10Donations = donationRepository.findLatest10Donations();
        dashboard.put("latestDonations", latest10Donations);

        return dashboard;
    }
}