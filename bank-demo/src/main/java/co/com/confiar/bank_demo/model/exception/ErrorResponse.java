package co.com.confiar.bank_demo.model.exception;

import co.com.confiar.bank_demo.model.entity.OperationCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(int statusCode, String message)
                            {
}
