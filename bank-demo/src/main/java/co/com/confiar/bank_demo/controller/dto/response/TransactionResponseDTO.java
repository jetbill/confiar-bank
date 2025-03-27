package co.com.confiar.bank_demo.controller.dto.response;

import co.com.confiar.bank_demo.model.entity.TransactionType;

import java.math.BigDecimal;

public record TransactionResponseDTO(String code,
                                     String message,
                                     BigDecimal balance,
                                     TransactionType type){
}
