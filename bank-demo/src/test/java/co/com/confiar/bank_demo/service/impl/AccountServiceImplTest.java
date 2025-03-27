package co.com.confiar.bank_demo.service.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import co.com.confiar.bank_demo.controller.dto.request.CreateAccountDTO;
import co.com.confiar.bank_demo.controller.dto.response.AccountResponseDTO;
import co.com.confiar.bank_demo.controller.mappers.AccountMapper;
import co.com.confiar.bank_demo.model.entity.Account;
import co.com.confiar.bank_demo.model.entity.Client;
import co.com.confiar.bank_demo.model.entity.OperationCode;
import co.com.confiar.bank_demo.model.exception.AccountAlreadyExistsException;
import co.com.confiar.bank_demo.model.exception.ClientNotFoundException;
import co.com.confiar.bank_demo.repository.AccountRepository;
import co.com.confiar.bank_demo.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private CreateAccountDTO validRequest;
    private Client existingClient;
    private Account savedAccount;
    private AccountResponseDTO expectedResponse;

    @BeforeEach
    void setUp() {
        validRequest = new CreateAccountDTO("1234567890", "0034981234");
        existingClient = Client.builder()
                .nit("1234567890")
                .name("Ronal Ruiz")
                .entryDate(LocalDate.now())
                .build();
        savedAccount = Account.builder()
                .accountNumber("0034981234")
                .balance(BigDecimal.ZERO)
                .client(existingClient)
                .createdAt(LocalDateTime.now())
                .build();
        expectedResponse = new AccountResponseDTO(
                OperationCode.CREATED_ACCOUNT_CODE.getDescription(),
                OperationCode.CREATED_ACCOUNT_MESSAGE.getDescription(),
                "0034981234"
        );
    }

    @Test
    void createAccount_SuccessfulCreation() {
        // Arrange
        when(clientRepository.findByNit(validRequest.clientNit())).thenReturn(Optional.of(existingClient));
        when(accountRepository.existsAccountByAccountNumber(validRequest.accountNumber())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        try (MockedStatic<AccountMapper> mockedMapper = Mockito.mockStatic(AccountMapper.class)) {
            mockedMapper.when(() -> AccountMapper.toAccountResponseDTO(savedAccount))
                    .thenReturn(expectedResponse);

            // Act
            AccountResponseDTO result = accountService.createAccount(validRequest);

            // Assert
            assertNotNull(result);
            assertEquals(expectedResponse, result);

            // Verify account creation
            ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
            verify(accountRepository).save(accountCaptor.capture());
            Account capturedAccount = accountCaptor.getValue();
            assertEquals(validRequest.accountNumber(), capturedAccount.getAccountNumber());
            assertEquals(BigDecimal.ZERO, capturedAccount.getBalance());
            assertEquals(existingClient, capturedAccount.getClient());

            verify(clientRepository).findByNit(validRequest.clientNit());
            verify(accountRepository).existsAccountByAccountNumber(validRequest.accountNumber());
            verify(accountRepository).save(any(Account.class));
            mockedMapper.verify(() -> AccountMapper.toAccountResponseDTO(savedAccount), times(1));
        }
    }

    @Test
    void createAccount_ClientNotFound() {
        // Arrange
        when(clientRepository.findByNit(validRequest.clientNit())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClientNotFoundException.class, () -> accountService.createAccount(validRequest));
        verify(clientRepository).findByNit(validRequest.clientNit());
        verify(accountRepository, never()).existsAccountByAccountNumber(anyString());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void createAccount_AccountAlreadyExists() {
        // Arrange
        when(clientRepository.findByNit(validRequest.clientNit())).thenReturn(Optional.of(existingClient));
        when(accountRepository.existsAccountByAccountNumber(validRequest.accountNumber())).thenReturn(true);

        // Act & Assert
        assertThrows(AccountAlreadyExistsException.class, () -> accountService.createAccount(validRequest));
        verify(clientRepository).findByNit(validRequest.clientNit());
        verify(accountRepository).existsAccountByAccountNumber(validRequest.accountNumber());
        verify(accountRepository, never()).save(any(Account.class));
    }

}