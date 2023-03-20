package br.com.escola.admin.controllers;

import br.com.escola.admin.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class EscolaControllerAdvice {
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex
            , HttpServletRequest request) {

        String error = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;

        var standardError = new StandardError(LocalDateTime.now(), status.value(), error
                , ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandardError> handleBusinessRuleException(BusinessRuleException ex
            , HttpServletRequest request) {

        String error = "Conflito";
        HttpStatus status = HttpStatus.CONFLICT;

        var standardError = new StandardError(LocalDateTime.now(), status.value(), error
                , ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationStandardError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) throws JsonProcessingException {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream()
                .map(fieldError -> fieldError.getField())
                .sorted()
                .collect(Collectors.joining(", "));

        String fieldsMessage = fieldErrors.stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(", "));

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Bad Request";
        String message = "Verifique os campo(s) com erro";

        var standardError = new ValidationStandardError(LocalDateTime.now(), status.value(), error
                , message, request.getRequestURI());

        standardError.setFields(fields);
        standardError.setFieldsMessage(fieldsMessage);

        return ResponseEntity.status(status).body(standardError);
    }

}
