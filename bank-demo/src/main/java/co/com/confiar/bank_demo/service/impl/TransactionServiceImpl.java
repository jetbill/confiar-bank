package co.com.confiar.bank_demo.service.impl;


import co.com.confiar.bank_demo.controller.dto.request.CreateTransactionDTO;
import co.com.confiar.bank_demo.controller.dto.response.TransactionResponseDTO;
import co.com.confiar.bank_demo.controller.mappers.TransactionMapper;
import co.com.confiar.bank_demo.model.entity.Account;
import co.com.confiar.bank_demo.model.entity.Transaction;
import co.com.confiar.bank_demo.model.entity.TransactionType;
import co.com.confiar.bank_demo.model.exception.AccountNotFoundException;
import co.com.confiar.bank_demo.model.exception.InsufficientFundsException;
import co.com.confiar.bank_demo.repository.AccountRepository;
import co.com.confiar.bank_demo.repository.TransactionRepository;
import co.com.confiar.bank_demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;



    @Override
    public TransactionResponseDTO createTransaction(CreateTransactionDTO trnDto) {

        return accountRepository.findById(trnDto.accountId())
                .map(account -> validateAndProcessTransaction(account, trnDto.type(), trnDto.amount()))
                .map(account -> saveTransactionAndUpdateBalance(account, trnDto.type(), trnDto.amount()))
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + trnDto.accountId()));
    }

    private Account validateAndProcessTransaction(Account account,
                                                  TransactionType type, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        return switch (type) {
            case DEBIT -> validateDebit(account, amount);
            case CREDIT -> updateCredit(account, amount);
            default -> throw new IllegalArgumentException("Invalid transaction type");
        };
    }

    private Account validateDebit(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    String.format("Insufficient balance. Current: %s, Required: %s",
                            account.getBalance(), amount));
        }
        account.setBalance(account.getBalance().subtract(amount));
        return account;
    }

    private Account updateCredit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        return account;
    }

    private TransactionResponseDTO saveTransactionAndUpdateBalance(Account account,
                                                                   TransactionType type,
                                                                   BigDecimal amount) {
        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(type == TransactionType.DEBIT ? amount.negate() : amount)
                .transactionType(type)
                .transactionDate(LocalDateTime.now())
                .build();

        accountRepository.save(account);
        return TransactionMapper.toTransactionResponseDTO(transactionRepository.save(transaction));
    }
}
