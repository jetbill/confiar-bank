package co.com.confiar.bank_demo.controller.mappers;


import co.com.confiar.bank_demo.controller.dto.request.CreateAccountDTO;
import co.com.confiar.bank_demo.controller.dto.response.AccountResponseDTO;
import co.com.confiar.bank_demo.model.entity.Account;
import co.com.confiar.bank_demo.model.entity.OperationCode;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;


public class AccountMapper {
    public static Account toAccount(CreateAccountDTO request){
        if(request == null) return null;
        return  Account.builder()
                .accountNumber(request.accountNumber())
                .balance(BigDecimal.ZERO)
                .build();

    }

    //List<Account> toAccountList(List<CreateAccountDTO> request);
    public static AccountResponseDTO toAccountResponseDTO(Account account){
        if(account == null) return null;
        return new AccountResponseDTO(OperationCode.CREATED_ACCOUNT_CODE.getDescription(),
                OperationCode.CREATED_ACCOUNT_MESSAGE.getDescription(),
                account.getAccountNumber());

    }
}
