package br.com.escola.admin.utils.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPFValidatorTest {


    @Test
    @DisplayName("Deve retornar true para CPF válido")
    void deveRetornarTrueParaCpfValido() {
        String cpf = "12345678909";
        boolean resultado = CPFValidator.isValid(cpf);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Deve retornar false para CPF inválido")
    void deveRetornarFalseParaCpfInvalido() {
        String cpf = "12345678900";
        boolean resultado = CPFValidator.isValid(cpf);
        assertFalse(resultado);
    }

}