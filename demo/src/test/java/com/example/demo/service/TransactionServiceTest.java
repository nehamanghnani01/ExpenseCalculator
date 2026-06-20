package com.example.demo.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.entity.Category;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void updateTransaction_ShouldUpdateTransaction() {

        Category category = new Category();
        category.setName("Category 1");
        category.setType(TransactionType.INCOME);
        category.setId(1);

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setDescription("Transaction 1");
        transaction.setCategory(category);
        transaction.setId(1);

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
        // Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        Mockito.when(transactionRepository.findById(1)).thenReturn(java.util.Optional.of(transaction));

        CreateTransactionRequest updatedTransactionRequest = new CreateTransactionRequest();
        updatedTransactionRequest.setAmount(BigDecimal.valueOf(2000));
        updatedTransactionRequest.setDescription("Updated Transaction 1");
        updatedTransactionRequest.setCategoryId(1);

        transactionService.updateTransaction(transaction.getId(), updatedTransactionRequest);

        Mockito.verify(transactionRepository).save(Mockito.any(Transaction.class));

        assert (transaction.getDescription().equals("Updated Transaction 1"));
        assert (transaction.getAmount().equals(BigDecimal.valueOf(2000)));
    }

    @Test
    public void updateTransaction_ShouldThrowException_WhenTransactionNotFound() {

        CreateTransactionRequest updatedTransactionRequest = new CreateTransactionRequest();
        updatedTransactionRequest.setAmount(BigDecimal.valueOf(2000));
        updatedTransactionRequest.setDescription("Updated Transaction 1");
        updatedTransactionRequest.setCategoryId(1);

        // Mockito.when(categoryRepository.findById(999)).thenReturn(java.util.Optional.empty());
        // Mockito.when(transactionRepository.findById(999)).thenReturn(java.util.Optional.empty());
        try {
            transactionService.updateTransaction(999, updatedTransactionRequest);
        } catch (Exception e) {
            assert (e.getMessage().equals("No value present"));
        }

    }

    @Test
    public void createTransaction_ShouldCreateTransaction() {
        Category category = new Category();
        category.setName("Category 1");
        category.setType(TransactionType.INCOME);
        category.setId(1);

        Mockito.when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));

        CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
        transactionRequest.setAmount(BigDecimal.valueOf(1000));
        transactionRequest.setDescription("Transaction 1");
        transactionRequest.setCategoryId(1);

        transactionService.createTransaction(transactionRequest);

        Mockito.verify(transactionRepository).save(Mockito.any(Transaction.class));
    }

    @Test
    public void deleteTransactionShouldDeleteTheTransaction() {

        Transaction transaction = new Transaction();
        transaction.setDescription("Transaction to Delete");
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setId(1);
        transactionService.deleteTransaction(transaction.getId());

        // verify that the deleteById method was called with the correct id
        Mockito.verify(transactionRepository).deleteById(transaction.getId());
    }

    @Test
    public void allTransactionsShouldReturnAllTransactions() {

        Category incomeCategory = new Category();
        incomeCategory.setName("Income");
        incomeCategory.setType(TransactionType.INCOME);
        incomeCategory.setId(1);

        Category expenseCategory = new Category();
        expenseCategory.setName("Expenses");
        expenseCategory.setType(TransactionType.EXPENSE);
        expenseCategory.setId(2);

        Transaction transaction1 = new Transaction();
        transaction1.setDescription("Transaction 1");
        transaction1.setAmount(BigDecimal.valueOf(5000));
        transaction1.setCategory(incomeCategory);
        transaction1.setId(1);

        Transaction transaction2 = new Transaction();
        transaction2.setDescription("Transaction 2");
        transaction2.setAmount(BigDecimal.valueOf(2000));
        transaction2.setCategory(expenseCategory);
        transaction2.setId(2);

        Mockito.when(transactionRepository.findAll()).thenReturn(java.util.List.of(transaction1, transaction2));

        java.util.List<Transaction> transactions = transactionService.allTransactions();

        assert (transactions.size() == 2);
        assert (transactions.get(0).getDescription().equals("Transaction 1"));
        assert (transactions.get(0).getAmount().equals(BigDecimal.valueOf(5000)));
        assert (transactions.get(1).getDescription().equals("Transaction 2"));
        assert (transactions.get(1).getAmount().equals(BigDecimal.valueOf(2000)));
    }

    @Test
    public void getAllTransactionsShouldReturnEmptyList_WhenNoTransactionsExist() {

        Mockito.when(transactionRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        java.util.List<Transaction> transactions = transactionService.allTransactions();

        assert (transactions.isEmpty());
    }

    @Test
    public void getTransation_WithMatchingDescription_ShouldReturnTransactions() {

        Category incomeCategory = new Category();
        incomeCategory.setName("Income");
        incomeCategory.setType(TransactionType.INCOME);
        incomeCategory.setId(1);

        Transaction transaction1 = new Transaction();
        transaction1.setDescription("Salary for June");
        transaction1.setAmount(BigDecimal.valueOf(5000));
        transaction1.setCategory(incomeCategory);
        transaction1.setId(1);

        Transaction transaction2 = new Transaction();
        transaction2.setDescription("Freelance Project");
        transaction2.setAmount(BigDecimal.valueOf(2000));
        transaction2.setCategory(incomeCategory);
        transaction2.setId(2);

        Mockito.when(transactionRepository.findByDescriptionContaining("Salary"))
                .thenReturn(java.util.List.of(transaction1));

        java.util.List<Transaction> transactions = transactionService.getTransactionsByDescription("Salary");

        assert (transactions.size() == 1);
        assert (transactions.get(0).getDescription().equals("Salary for June"));
    }

    // @Test
    // public void getTransation_WithAmountRange_ShouldReturnTransactions() {

    // Category incomeCategory = new Category();
    // incomeCategory.setName("Income");
    // incomeCategory.setType(TransactionType.INCOME);
    // incomeCategory.setId(1);

    // Transaction transaction1 = new Transaction();
    // transaction1.setDescription("Salary for June");
    // transaction1.setAmount(BigDecimal.valueOf(5000));
    // transaction1.setCategory(incomeCategory);
    // transaction1.setId(1);

    // Transaction transaction2 = new Transaction();
    // transaction2.setDescription("Freelance Project");
    // transaction2.setAmount(BigDecimal.valueOf(2000));
    // transaction2.setCategory(incomeCategory);
    // transaction2.setId(2);

    // Mockito.when(transactionRepository.findByAmountGreaterThanEqual(BigDecimal.valueOf(3000)))
    // .thenReturn(List.of(transaction1));

    // Mockito.when(transactionRepository.findByAmountLessThanEqual(BigDecimal.valueOf(6000)))
    // .thenReturn(List.of(transaction1, transaction2));

    // List<Transaction> transactions = transactionService
    // .getTransactionsByAmountRange(BigDecimal.valueOf(3000),
    // BigDecimal.valueOf(6000));

    // assert (transactions.size() == 1);
    // assert (transactions.get(0).getDescription().equals("Salary for June"));
    // }

}
