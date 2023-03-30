package br.com.escola.admin.controllers.handler;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.exceptions.StandardError;
import br.com.escola.admin.exceptions.ValidationStandardError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
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
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String fields = montarAtributosComErros(fieldErrors);
        String fieldsMessage = montarMensagensDeErro(fieldErrors);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Bad Request";
        String message = "Verifique os campo(s) com erro";

        var standardError = new ValidationStandardError(LocalDateTime.now(), status.value(), error
                , message, request.getRequestURI());

        standardError.setFields(fields);
        standardError.setFieldsMessage(fieldsMessage);

        return ResponseEntity.status(status).body(standardError);
    }

    private String montarMensagensDeErro(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(", "));
    }

    private String montarAtributosComErros(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> fieldError.getField())
                .sorted()
                .collect(Collectors.joining(", "));
    }


}
