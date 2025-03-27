package co.com.confiar.bank_demo.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ClientNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleClientExistException(ClientAlreadyExistsException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.ALREADY_REPORTED.value())
                .message("Client already exists")
                .build();
        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
    }
}
