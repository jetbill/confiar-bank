package co.com.confiar.bank_demo.service.impl;

import co.com.confiar.bank_demo.controller.dto.request.CreateAccountDTO;
import co.com.confiar.bank_demo.controller.dto.response.AccountResponseDTO;
import co.com.confiar.bank_demo.controller.mappers.AccountMapper;
import co.com.confiar.bank_demo.model.entity.Account;
import co.com.confiar.bank_demo.model.entity.Client;
import co.com.confiar.bank_demo.model.exception.AccountAlreadyExistsException;
import co.com.confiar.bank_demo.model.exception.ClientNotFoundException;
import co.com.confiar.bank_demo.repository.AccountRepository;
import co.com.confiar.bank_demo.repository.ClientRepository;
import co.com.confiar.bank_demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final ClientRepository clientRepository;
    private  final AccountRepository accountRepository;



    @Override
    public AccountResponseDTO createAccount(CreateAccountDTO request) {
        return clientRepository.findByNit(request.clientNit())
                .map(client -> validateAccount(request, client))
                .map(accountRepository::save)
                .map(AccountMapper::toAccountResponseDTO)
                .orElseThrow(() -> new ClientNotFoundException(
                        String.format("Client with NIT %s not found", request.clientNit())));

    }
    private Account validateAccount(CreateAccountDTO request, Client client) {
        if (accountRepository.existsAccountByAccountNumber(request.accountNumber())) {
            throw new AccountAlreadyExistsException(
                    String.format("Account with number %s already exists", request.accountNumber()));
        }

        return Account.builder()
                .accountNumber(request.accountNumber())
                .balance(BigDecimal.ZERO)
                .client(client)
                .build();
    }
}
