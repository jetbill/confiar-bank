package co.com.confiar.bank_demo.controller.dto.response;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ClientResponseDTO(String nit,
                                String name,
                                LocalDate entryDate) {
}
