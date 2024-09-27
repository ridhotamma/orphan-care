package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.service.AnalyticService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/analytics")
@RequiredArgsConstructor
public class AnalyticController {

    private final AnalyticService analyticService;

    @GetMapping("/homepage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getHomePageAnalytics() {
        return ResponseEntity.ok(analyticService.getHomePageAnalytic());
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getDashboardAnalytics() {
        return ResponseEntity.ok(analyticService.getDashboardAnalytics());
    }
}