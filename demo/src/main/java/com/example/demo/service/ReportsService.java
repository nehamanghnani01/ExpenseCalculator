package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SummaryResponse;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;
import com.example.demo.repository.TransactionRepository;

@Service
public class ReportsService {

    private final TransactionRepository transactionRepository;

    public ReportsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public SummaryResponse generateSummaryReport() {

        List<Transaction> transactions = transactionRepository.findAll();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getCategory().getType().equals(TransactionType.INCOME)) {
                totalIncome = totalIncome.add(transaction.getAmount());
            } else if (transaction.getCategory().getType().equals(TransactionType.EXPENSE)) {
                totalExpense = totalExpense.add(transaction.getAmount());
            }
        }

        SummaryResponse response = new SummaryResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setNetBalance(totalIncome.subtract(totalExpense));
        return response;
    }

    public SummaryResponse generateMonthlySummaryReport(int month, int year) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(
                year,
                month,
                Month.of(month)
                        .length(Year.isLeap(year)));

        List<Transaction> transactions = transactionRepository.findByDateBetween(
                startDate,
                endDate);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getCategory().getType().equals(TransactionType.INCOME)) {
                totalIncome = totalIncome.add(transaction.getAmount());
            } else if (transaction.getCategory().getType().equals(TransactionType.EXPENSE)) {
                totalExpense = totalExpense.add(transaction.getAmount());
            }
        }

        SummaryResponse response = new SummaryResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setNetBalance(totalIncome.subtract(totalExpense));
        return response;
    }

}
