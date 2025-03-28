package co.com.confiar.bank_demo.controller;

import co.com.confiar.bank_demo.controller.dto.request.CreateTransactionDTO;
import co.com.confiar.bank_demo.controller.dto.response.TransactionResponseDTO;
import co.com.confiar.bank_demo.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/{accountId}")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody @Valid CreateTransactionDTO request,
                                                                    @PathVariable long accountId) {
        return new ResponseEntity<>(transactionService.createTransaction(request,accountId), HttpStatus.CREATED);
    }

}
