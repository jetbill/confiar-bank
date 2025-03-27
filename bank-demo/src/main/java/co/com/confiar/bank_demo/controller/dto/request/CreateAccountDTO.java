package co.com.confiar.bank_demo.controller.dto.request;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public record CreateAccountDTO(@Size(max = 10, message = "{generic.size}")
                               String clientNit,
                               @Pattern(regexp = "^\\d{1,10}$", message = "{saveAccount.number.pattern}")
                               String accountNumber
                               ) {
}
