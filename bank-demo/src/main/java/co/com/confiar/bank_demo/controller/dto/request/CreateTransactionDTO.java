package co.com.confiar.bank_demo.controller.dto.request;

import co.com.confiar.bank_demo.model.entity.TransactionType;

import java.math.BigDecimal;

public record CreateTransactionDTO(Long accountId,
                                   BigDecimal amount,
                                   TransactionType type) {
}
