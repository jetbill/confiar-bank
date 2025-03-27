package co.com.confiar.bank_demo.controller;


import co.com.confiar.bank_demo.controller.dto.request.CreateAccountDTO;
import co.com.confiar.bank_demo.controller.dto.response.AccountResponseDTO;
import co.com.confiar.bank_demo.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid CreateAccountDTO request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }
}
