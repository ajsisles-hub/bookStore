package com.project.bookStore.exception;


import com.project.bookStore.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDatabaseConstraints(DataIntegrityViolationException ex) {

        String rootMsg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";
        Map<String, Object> fieldErrors = new HashMap<>();

        if (rootMsg.toLowerCase().contains("email")) {
            fieldErrors.put("email", "This email address is already registered.");
        }

        if (rootMsg.toLowerCase().contains("name")) {
            fieldErrors.put("name", "This name is already taken.");
        }

        // Fallback default error message if parsing fails
        String summaryMessage = fieldErrors.isEmpty() ? "Database unique constraint violation." : "Validation failed.";
        ErrorResponse response = new ErrorResponse(summaryMessage, fieldErrors);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409 Conflict
    }

}
