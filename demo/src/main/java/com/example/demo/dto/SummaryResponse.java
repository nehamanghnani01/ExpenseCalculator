package com.example.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;

public class SummaryResponse {

    @Positive
    BigDecimal totalIncome;

    BigDecimal totalExpense;

    BigDecimal netBalance;

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public BigDecimal getNetBalance() {
        return netBalance;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public void setNetBalance(BigDecimal netBalance) {
        this.netBalance = netBalance;
    }

}
