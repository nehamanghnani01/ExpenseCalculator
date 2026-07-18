package com.example.demo.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByCategoryId(Integer categoryId);

    default List<Transaction> findByCategoryType(TransactionType type) {
        List<Transaction> transactions = findAll();
        return transactions.stream()
                .filter(t -> t.getCategory().getType() == type)
                .toList();
    }

    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);

    List<Transaction> findByDescriptionContaining(String keyword);

    List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount);

    List<Transaction> findByAmountLessThanEqual(BigDecimal amount);

    List<Transaction> findByAmountBetween(BigDecimal min, BigDecimal max);

}
