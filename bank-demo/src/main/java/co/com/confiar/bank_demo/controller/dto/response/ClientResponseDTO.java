package co.com.confiar.bank_demo.controller.dto.response;

import java.time.LocalDate;

public record ClientResponseDTO(String nit,
                                String name,
                                LocalDate entryDate) {
}
