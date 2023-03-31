package br.com.escola.admin.exceptions;

import br.com.escola.admin.models.Curso;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

	public ResourceNotFoundException(Object id) {
        super("Recurso não encontrado. Id = " + id, null, false, false);
    }

    public ResourceNotFoundException(Class<?> recurso, Long id) {
        super(recurso.getSimpleName() + " nao encontrado, Id = " + id);
    }

}
