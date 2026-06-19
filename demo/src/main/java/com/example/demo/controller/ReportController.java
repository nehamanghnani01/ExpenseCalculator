package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SummaryResponse;
import com.example.demo.service.ReportsService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportsService reportsService;

    public ReportController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping("/summary")
    public SummaryResponse getSummaryReport() {
        // Placeholder for summary report logic
        return reportsService.generateSummaryReport();
    }
}
