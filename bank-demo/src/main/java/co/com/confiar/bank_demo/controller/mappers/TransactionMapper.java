package co.com.confiar.bank_demo.controller.mappers;

import co.com.confiar.bank_demo.controller.dto.response.TransactionResponseDTO;
import co.com.confiar.bank_demo.model.entity.OperationCode;
import co.com.confiar.bank_demo.model.entity.Transaction;

public class TransactionMapper {
    public static TransactionResponseDTO toTransactionResponseDTO(Transaction transaction) {
        if (transaction == null) return null;
        return new TransactionResponseDTO(OperationCode.CREATE_TRANSACTION_CODE.getDescription(),
                OperationCode.CREATE_TRANSACTION_MESSAGE.getDescription(),
                transaction.getAmount(),
                transaction.getTransactionType());


    }
}
