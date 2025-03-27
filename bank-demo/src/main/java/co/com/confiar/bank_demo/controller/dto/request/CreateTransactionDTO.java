package co.com.confiar.bank_demo.controller.dto.request;

import co.com.confiar.bank_demo.model.entity.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionDTO(
        @NotNull(message = "The amount cannot be empty")
        @DecimalMin(value = "0.0", inclusive = true, message = "The amount cannot be negative")
        BigDecimal amount,
        @NotBlank(message = "You must specify the transaction type")
        TransactionType type) {
}
