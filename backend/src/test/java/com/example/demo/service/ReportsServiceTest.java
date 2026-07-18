package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.SummaryResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;
import com.example.demo.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class ReportsServiceTest {

        @Mock
        private TransactionRepository transactionRepository;

        @InjectMocks
        private ReportsService reportsService;

        @Test
        public void testGenerateSummaryReport() {

                // create the data
                Category incomeCategory = new Category();
                incomeCategory.setType(TransactionType.INCOME);

                Category expenseCategory = new Category();
                expenseCategory.setType(TransactionType.EXPENSE);

                Transaction income = new Transaction();
                income.setAmount(BigDecimal.valueOf(5000));
                income.setCategory(incomeCategory);

                Transaction expense = new Transaction();
                expense.setAmount(BigDecimal.valueOf(1000));
                expense.setCategory(expenseCategory);

                Transaction expense2 = new Transaction();
                expense2.setAmount(BigDecimal.valueOf(1000));
                expense2.setCategory(expenseCategory);

                when(transactionRepository.findAll())
                                .thenReturn(List.of(income, expense, expense2));

                SummaryResponse response = reportsService.generateSummaryReport();

                assertEquals(
                                BigDecimal.valueOf(5000),
                                response.getTotalIncome());

                assertNotEquals(
                                BigDecimal.valueOf(1000),
                                response.getTotalExpense());

                assertEquals(
                                BigDecimal.valueOf(3000),
                                response.getNetBalance());

        }

        @Test
        public void shouldReturnZeroIfNoTransactionExists() {

                // create the data
                Category incomeCategory = new Category();
                incomeCategory.setType(TransactionType.INCOME);

                Category expenseCategory = new Category();
                expenseCategory.setType(TransactionType.EXPENSE);

                when(transactionRepository.findAll())
                                .thenReturn(List.of());

                SummaryResponse response = reportsService.generateSummaryReport();

                assertEquals(
                                BigDecimal.ZERO,
                                response.getTotalIncome());

                assertEquals(
                                BigDecimal.ZERO,
                                response.getTotalExpense());

                assertEquals(
                                BigDecimal.ZERO,
                                response.getNetBalance());

        }

        @Test // only income transactions exist
        public void calculateNetIncomeCorrectly() {

                // create the data
                Category incomeCategory = new Category();
                incomeCategory.setType(TransactionType.INCOME);

                Category expenseCategory = new Category();
                expenseCategory.setType(TransactionType.EXPENSE);

                Transaction income = new Transaction();
                income.setAmount(BigDecimal.valueOf(5000));
                income.setCategory(incomeCategory);

                Transaction income2 = new Transaction();
                income2.setAmount(BigDecimal.valueOf(7000));
                income2.setCategory(incomeCategory);

                when(transactionRepository.findAll())
                                .thenReturn(List.of(income, income2));

                SummaryResponse response = reportsService.generateSummaryReport();

                assertEquals(
                                BigDecimal.valueOf(12000),
                                response.getTotalIncome());

                assertEquals(
                                BigDecimal.ZERO,
                                response.getTotalExpense());

                assertEquals(
                                BigDecimal.valueOf(12000),
                                response.getNetBalance());

        }

        @Test // only expense transactions exist
        public void calculateNetExpenseCorrectly() {

                // create the data
                Category expenseCategory = new Category();
                expenseCategory.setType(TransactionType.EXPENSE);

                Transaction expense = new Transaction();
                expense.setCategory(expenseCategory);
                expense.setAmount(BigDecimal.valueOf(1000));

                Transaction expense2 = new Transaction();
                expense2.setAmount(BigDecimal.valueOf(2000));
                expense2.setCategory(expenseCategory);

                when(transactionRepository.findAll())
                                .thenReturn(List.of(expense, expense2));

                SummaryResponse response = reportsService.generateSummaryReport();

                assertEquals(
                                BigDecimal.ZERO,
                                response.getTotalIncome());

                assertEquals(
                                BigDecimal.valueOf(3000),
                                response.getTotalExpense());

                assertEquals(
                                BigDecimal.valueOf(-3000),
                                response.getNetBalance());
        }

        @Test
        public void testMonthlySummaryReport() {

                // create the data
                Category expenseCategory = new Category();
                expenseCategory.setType(TransactionType.EXPENSE);

                Category incomeCategory = new Category();
                incomeCategory.setType(TransactionType.INCOME);

                Transaction income = new Transaction();
                income.setAmount(BigDecimal.valueOf(5000));
                income.setCategory(incomeCategory);
                income.setDate(java.time.LocalDate.of(2026, 6, 10));

                Transaction expense = new Transaction();
                expense.setCategory(expenseCategory);
                expense.setAmount(BigDecimal.valueOf(1000));
                expense.setDate(java.time.LocalDate.of(2026, 6, 15));

                Transaction expense2 = new Transaction();
                expense2.setAmount(BigDecimal.valueOf(2000));
                expense2.setCategory(expenseCategory);
                expense2.setDate(java.time.LocalDate.of(2026, 6, 20));

                Transaction expense3 = new Transaction();
                expense3.setAmount(BigDecimal.valueOf(2000));
                expense3.setCategory(expenseCategory);
                expense3.setDate(java.time.LocalDate.of(2026, 5, 20));

                when(transactionRepository.findByDateBetween(
                                java.time.LocalDate.of(2026, 6, 1),
                                java.time.LocalDate.of(2026, 6, 30)))
                                .thenReturn(List.of(income, expense, expense2));

                SummaryResponse response = reportsService.generateMonthlySummaryReport(6, 2026);

                assertEquals(
                                BigDecimal.valueOf(5000),
                                response.getTotalIncome());

                assertEquals(
                                BigDecimal.valueOf(3000),
                                response.getTotalExpense());

                assertEquals(
                                BigDecimal.valueOf(2000),
                                response.getNetBalance());

        }
}
