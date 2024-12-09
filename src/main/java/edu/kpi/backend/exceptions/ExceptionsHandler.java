package edu.kpi.backend.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionsHandler {
    @Data
    private static class ExceptionDetails {
        private Date timestamp;
        private String message;

        public ExceptionDetails(String message) {
            this.timestamp = new Date();
            this.message = message;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
        HttpStatusCode status = exception.getStatusCode();
        String reason = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();

        return new ResponseEntity<>(new ExceptionDetails(reason), status);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException exception) {
        return new ResponseEntity<>(new ExceptionDetails(exception.getMessage()), exception.getStatusCode());
    }
}
