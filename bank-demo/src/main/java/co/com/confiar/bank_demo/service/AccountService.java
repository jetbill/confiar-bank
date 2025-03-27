package co.com.confiar.bank_demo.service;

import co.com.confiar.bank_demo.controller.dto.request.CreateAccountDTO;
import co.com.confiar.bank_demo.controller.dto.response.AccountResponseDTO;

public interface AccountService {

    AccountResponseDTO createAccount(CreateAccountDTO request);


}
