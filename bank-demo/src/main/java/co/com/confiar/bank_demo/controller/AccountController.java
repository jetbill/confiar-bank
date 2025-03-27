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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts", description = "Operaciones relacionadas con cuentas bancarias")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;



    @Operation(
            summary = "Crear una cuenta",
            description = "Crea una nueva cuenta asociada a un cliente",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PostMapping
     public ResponseEntity<AccountResponseDTO> createAccount(
            @Parameter(description = "Datos de la cuenta a crear", required = true)
            @RequestBody @Valid CreateAccountDTO request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }
}
