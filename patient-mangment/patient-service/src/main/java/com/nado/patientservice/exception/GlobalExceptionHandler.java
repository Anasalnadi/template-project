package com.nado.patientservice.exception;

import com.nado.patientservice.exception.custom.EmailAlreadyExistsException;
import com.nado.patientservice.exception.custom.PatientNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Malformed JSON input: {}", ex.getMostSpecificCause().getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                false,
                "Invalid request body format. Please check your JSON syntax.",
                Map.of()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(),error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                false,
                "Constraint violation occurred.",
                errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleJdbcSQLIntegrityConstraintViolationException(JdbcSQLIntegrityConstraintViolationException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        String message = ex.getMessage();

        if (message != null && message.contains("EMAIL")) {
            fieldErrors.put("email", "This email is already registered.");
        } else {
            fieldErrors.put("error", "A database constraint was violated.");
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                false,
                "Constraint violation occurred.",
                fieldErrors
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        log.warn("Email is already exists {}", ex.getMessage());

        Map<String,String> errors = new HashMap<>();

        errors.put("email","Email is already exists");

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                false,
                "Constraint violation occurred.",
                errors
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException ex) {

        log.warn("Patient not exists {}", ex.getMessage());

        Map<String,String> errors = new HashMap<>();

        errors.put("id",ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                false,
                "Patient not found",
                errors
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

    }

    // Handle database constraint violations (unique keys, foreign keys, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                false,
                "Database constraint violation",
                Map.of("message", "Database error: " + ex.getRootCause().getMessage())
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Handle date parsing errors (invalid date formats)
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(DateTimeParseException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                false,
                "Invalid date format",
                Map.of("message", "Date must be in ISO format yyyy-MM-dd")
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Handle ConstraintViolationException from validation annotations on method params
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
            errors.put(field, violation.getMessage());
        });

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                false,
                "Validation failed",
                errors);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Catch-all for all other exceptions (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUnhandledExceptions(Exception ex) {
        ex.printStackTrace(); // Optional: log the stack trace

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                false,
                "Internal server error",
                Map.of("message", "An unexpected error occurred. Please try again later.")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
