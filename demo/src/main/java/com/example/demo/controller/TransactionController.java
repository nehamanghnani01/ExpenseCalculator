package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.allTransactions();
    }

    @PostMapping
    public void createTransaction(@Valid @RequestBody CreateTransactionRequest transaction) {
        transactionService.createTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
    }

    @PatchMapping("/{id}")
    public void updateTransaction(@PathVariable Integer id, @RequestBody CreateTransactionRequest transaction) {
        transactionService.updateTransaction(id, transaction);
    }

    @GetMapping("/description")
    public List<Transaction> getTransactionsByDescription(@RequestParam String keyword) {
        return transactionService.getTransactionsByDescription(keyword);
    }

    @GetMapping("/amount")
    public List<Transaction> getTransactionsByAmountRange(@RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max) {
        return transactionService.getTransactionsByAmountRange(min, max);
    }

    // @GetMapping("/category")
    // public List<Transaction> getTransactionsByCategoryName(@RequestParam String
    // name) {
    // return transactionService.getTransactionsByCategoryName(name);
    // }

}
