package com.thunderstormers.healthfuel.error;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Hidden
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error: ", ex);
        return ApiResponse.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).toResponseEntity();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationException(ConstraintViolationException ex) {
        log.error("Validation error: ", ex);
        return ApiResponse.error(
                ex.getConstraintViolations().stream()
                        .map((cv) -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage()).toList(),
                HttpStatus.BAD_REQUEST).toResponseEntity();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBaseException(BaseException ex) {
        log.error("Domain error: ", ex);
        return ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST).toResponseEntity();
    }

}
