package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.entity.Gender;
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
    private final ProfileRepository profileRepository;
    private final EventRepository eventRepository;

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

        List<Map<String, Object>> latest10Events = eventRepository.findLatest10Events(); // Add this line
        dashboard.put("latestEvents", latest10Events);

        dashboard.put("profile", this.getProfileAnalytics());
        dashboard.put("inventory", this.getInventoryAnalytics());
        dashboard.put("bedroom", this.getBedroomAnalytics());

        return dashboard;
    }

    private Map<String, Object> getProfileAnalytics() {
        // Define date ranges
        LocalDate now = LocalDate.now();
        LocalDate currentYearStart = LocalDate.of(now.getYear(), 1, 1);
        LocalDate previousYearStart = currentYearStart.minusYears(1);
        LocalDate previousYearEnd = currentYearStart.minusDays(1);

        // Male calculations
        long totalMaleCount = profileRepository.countByGender(Gender.MALE);
        long currentYearMaleCount = profileRepository.countByGenderAndJoinDateBetween(Gender.MALE, currentYearStart, now);
        long previousYearMaleCount = profileRepository.countByGenderAndJoinDateBetween(Gender.MALE, previousYearStart, previousYearEnd);
        String maleDifference = calculatePercentageDifference(previousYearMaleCount, currentYearMaleCount);

        // Female calculations
        long totalFemaleCount = profileRepository.countByGender(Gender.FEMALE);
        long currentYearFemaleCount = profileRepository.countByGenderAndJoinDateBetween(Gender.FEMALE, currentYearStart, now);
        long previousYearFemaleCount = profileRepository.countByGenderAndJoinDateBetween(Gender.FEMALE, previousYearStart, previousYearEnd);
        String femaleDifference = calculatePercentageDifference(previousYearFemaleCount, currentYearFemaleCount);

        // Create profile mapping
        Map<String, Object> profileMapping = new HashMap<>();

        // Male data
        profileMapping.put("maleCount", totalMaleCount);
        profileMapping.put("currentYearMaleCount", currentYearMaleCount);
        profileMapping.put("previousYearMaleCount", previousYearMaleCount);
        profileMapping.put("maleDifference", maleDifference);

        // Female data
        profileMapping.put("femaleCount", totalFemaleCount);
        profileMapping.put("currentYearFemaleCount", currentYearFemaleCount);
        profileMapping.put("previousYearFemaleCount", previousYearFemaleCount);
        profileMapping.put("femaleDifference", femaleDifference);

        return profileMapping;
    }

    private Map<String, Object> getBedroomAnalytics() {
        Map<String, Object> bedroomAnalytics = new HashMap<>();

        // Get total bedroom count
        long totalBedrooms = bedRoomRepository.count();
        bedroomAnalytics.put("total", totalBedrooms);

        // Get bedroom details by type
        List<Map<String, Object>> bedroomDetails = bedRoomRepository.getBedroomCountByType();
        bedroomAnalytics.put("details", bedroomDetails);

        return bedroomAnalytics;
    }

    private Map<String, Object> getInventoryAnalytics() {
        Map<String, Object> inventoryMapping = new HashMap<>();

        // Get total inventory count
        long totalInventory = inventoryRepository.count();
        inventoryMapping.put("total", totalInventory);

        // Get inventory details by type
        List<Map<String, Object>> inventoryDetails = inventoryRepository.getInventoryCountByType();
        inventoryMapping.put("details", inventoryDetails);

        return inventoryMapping;
    }

    private String calculatePercentageDifference(long previousCount, long currentCount) {
        if (previousCount == 0) {
            return currentCount == 0 ? "0%" : "N/A";
        }
        double percentageDifference = ((double) (currentCount - previousCount) / previousCount) * 100;
        return String.format("%.2f%%", percentageDifference);
    }
}
