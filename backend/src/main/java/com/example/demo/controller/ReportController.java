package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return reportsService.generateSummaryReport();
    }

    @GetMapping("/summary/monthly")
    public SummaryResponse getMonthlySummaryReport(@RequestParam int month, @RequestParam int year) {
        return reportsService.generateMonthlySummaryReport(month, year);
    }
}
