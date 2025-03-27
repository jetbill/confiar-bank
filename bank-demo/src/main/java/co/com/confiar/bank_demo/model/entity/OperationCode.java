package co.com.confiar.bank_demo.model.entity;

import lombok.Getter;

@Getter
public enum OperationCode {
    CREATED_ACCOUNT_CODE("2004"),
    CREATED_ACCOUNT_MESSAGE("cuenta creada exitosamente"),
    CREATE_TRANSACTION_CODE("2005"),
    CREATE_TRANSACTION_MESSAGE("Transaccion exitosa"),;


    private final String description;

    OperationCode(String description) {
        this.description = description;

    }
}
