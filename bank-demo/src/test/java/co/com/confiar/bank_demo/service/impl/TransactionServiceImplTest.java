package co.com.confiar.bank_demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import co.com.confiar.bank_demo.controller.dto.request.CreateTransactionDTO;
import co.com.confiar.bank_demo.controller.dto.response.TransactionResponseDTO;
import co.com.confiar.bank_demo.model.entity.Account;
import co.com.confiar.bank_demo.model.entity.Transaction;
import co.com.confiar.bank_demo.model.entity.TransactionType;
import co.com.confiar.bank_demo.model.exception.AccountNotFoundException;
import co.com.confiar.bank_demo.model.exception.InsufficientFundsException;
import co.com.confiar.bank_demo.repository.AccountRepository;
import co.com.confiar.bank_demo.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account testAccount;
    private CreateTransactionDTO debitDto;
    private CreateTransactionDTO creditDto;
    private Transaction savedTransaction;

    @BeforeEach
    void setUp() {
        testAccount = Account.builder()
                .id(1L)
                .accountNumber("0034981234")
                .balance(new BigDecimal("1000.00"))
                .build();

        debitDto = new CreateTransactionDTO(new BigDecimal("300.00"), TransactionType.DEBIT);
        creditDto = new CreateTransactionDTO(new BigDecimal("500.00"), TransactionType.CREDIT);

        savedTransaction = Transaction.builder()
                .id(1L)
                .amount(new BigDecimal("500.00"))
                .transactionType(TransactionType.DEBIT)
                .transactionDate(LocalDateTime.now())
                .account(testAccount)
                .build();
    }

    @Test
    void createTransaction_Credit_Success() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(
                Transaction.builder()
                        .id(2L)
                        .amount(new BigDecimal("300.00"))
                        .transactionType(TransactionType.CREDIT)
                        .transactionDate(LocalDateTime.now())
                        .account(testAccount)
                        .build()
        );

        // Act
        TransactionResponseDTO result = transactionService.createTransaction(creditDto, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("300.00"), result.amount());
        assertEquals(TransactionType.CREDIT, result.type());

        // Verify account balance updated
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        Account updatedAccount = accountCaptor.getValue();
        assertEquals(new BigDecimal("1500.00"), updatedAccount.getBalance());
    }





    @Test
    void createTransaction_AccountNotFound() {
        // Arrange
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () ->
                transactionService.createTransaction(debitDto, 99L)
        );
    }

    @Test
    void createTransaction_NegativeAmount() {
        // Arrange
        CreateTransactionDTO invalidDto = new CreateTransactionDTO(
                new BigDecimal("-100.00"),
                TransactionType.DEBIT
        );
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                transactionService.createTransaction(invalidDto, 1L)
        );
    }



}