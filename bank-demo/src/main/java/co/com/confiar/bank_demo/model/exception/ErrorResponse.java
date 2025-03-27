package co.com.confiar.bank_demo.model.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(int code, String message) {
}
