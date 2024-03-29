package br.com.escola.admin.controllers;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ErrorDTO;
import br.com.escola.admin.exceptions.ErrorFormDTO;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class EscolaControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorDTO(ex.getMessage(), "404");
    }

    @ExceptionHandler(value = {BusinessRuleException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBusinessRuleException(Exception ex) {
        return new ErrorDTO(ex.getMessage(), "400");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorFormDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors();

        return errors.stream()
                .map(error -> new ErrorFormDTO(error.getField(), error.getDefaultMessage()))
                .toList();
    }

}
