package co.com.confiar.bank_demo.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateClientDTO(@Size(max = 10, message = "Must contain up to 10 characters") String nit,
                              @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters.")
                              String name,
                              @PastOrPresent(message = "The entry date cannot be in the future")
                              LocalDate entryDate) {
}
