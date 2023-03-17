package br.com.escola.admin.controllers;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ErrorDTO;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.exceptions.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class EscolaControllerAdvice {
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex
            , HttpServletRequest request) {

        String error = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;

        var standardError = new StandardError(Instant.now(), status.value(), error
                , ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandardError> handleBusinessRuleException(BusinessRuleException ex
            , HttpServletRequest request) {

        String error = "Conflito";
        HttpStatus status = HttpStatus.CONFLICT;

        var standardError = new StandardError(Instant.now(), status.value(), error
                , ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

}
