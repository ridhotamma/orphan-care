package org.orphancare.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.repository.*;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Map<String, Object>> formattedTrends = formatDonationTrends(donationTrends);
        dashboard.put("donationTrends", formattedTrends);

        List<Map<String, Object>> donationTypeDistribution = donationRepository.getDonationTypeDistribution();
        dashboard.put("donationTypeDistribution", donationTypeDistribution);

        List<Map<String, Object>> totalDonationAmount = donationRepository.getTotalDonationAmountByType();
        List<Map<String, Object>> formattedTotalDonationAmount = formatTotalDonationAmount(totalDonationAmount);
        dashboard.put("totalDonationAmount", formattedTotalDonationAmount);

        List<Map<String, Object>> topDonors = donationRepository.findTop5DonorsWithTypeAndUnit();
        dashboard.put("topDonors", topDonors);

        return dashboard;
    }

    private List<Map<String, Object>> formatDonationTrends(List<Map<String, Object>> donationTrends) {
        DecimalFormat df = new DecimalFormat("#,##0.##");

        return donationTrends.stream()
                .collect(Collectors.groupingBy(
                        trend -> trend.get("donationName"),
                        Collectors.mapping(trend -> {
                            Map<String, Object> detail = new HashMap<>();
                            String[] amountParts = ((String) trend.get("amount")).split(" ", 2);
                            Double amount = Double.parseDouble(amountParts[0]);
                            String unit = amountParts.length > 1 ? amountParts[1] : "";

                            detail.put("amount", df.format(amount) + " " + unit);
                            detail.put("month", trend.get("month"));
                            return detail;
                        }, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> formattedTrend = new HashMap<>();
                    formattedTrend.put("donationName", entry.getKey());
                    formattedTrend.put("details", entry.getValue());
                    return formattedTrend;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> formatTotalDonationAmount(List<Map<String, Object>> totalDonationAmount) {
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return totalDonationAmount.stream()
                .map(donation -> {
                    Map<String, Object> formattedDonation = new HashMap<>();
                    formattedDonation.put("name", donation.get("name"));

                    Double amount = ((Number) donation.get("amount")).doubleValue();
                    String unit = (String) donation.get("unit");
                    String formattedAmount = df.format(amount) + " " + unit;

                    formattedDonation.put("amount", formattedAmount);
                    return formattedDonation;
                })
                .collect(Collectors.toList());
    }
}