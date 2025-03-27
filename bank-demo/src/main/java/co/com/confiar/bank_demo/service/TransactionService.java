package co.com.confiar.bank_demo.service;

import co.com.confiar.bank_demo.controller.dto.request.CreateTransactionDTO;
import co.com.confiar.bank_demo.controller.dto.response.TransactionResponseDTO;
import co.com.confiar.bank_demo.model.entity.TransactionType;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionResponseDTO createTransaction(CreateTransactionDTO createTransactionDTO);
}
