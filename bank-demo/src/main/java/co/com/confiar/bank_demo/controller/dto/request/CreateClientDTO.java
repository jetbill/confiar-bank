package co.com.confiar.bank_demo.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateClientDTO(@Size(max = 10, message = "{generic.size}") String nit,
                              @Size(min = 3, max = 50, message = "{saveUser.username.pattern}")
                              String name,
                              LocalDate entryDate) {
}
