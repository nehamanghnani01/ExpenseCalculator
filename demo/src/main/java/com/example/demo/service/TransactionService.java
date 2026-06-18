package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.entity.Category;
import com.example.demo.entity.Transaction;
import com.example.demo.exception.InvalidAmountException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction();
        if (request.getAmount().compareTo(new BigDecimal("50000.00")) > 0) {
            throw new InvalidAmountException("Amount must be less than 50000");
        }
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());

        LocalDate today = LocalDate.now();
        transaction.setDate(today);

        LocalDateTime now = LocalDateTime.now();

        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow();

        transaction.setCategory(category);
        transactionRepository.save(transaction);
    }

    public List<Transaction> allTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Integer id) {
        transactionRepository.deleteById(id);
    }

    public void updateTransaction(Integer id, CreateTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow();

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());

        LocalDateTime now = LocalDateTime.now();
        transaction.setUpdatedAt(now);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow();

        transaction.setCategory(category);
        transactionRepository.save(transaction);
    }

}
