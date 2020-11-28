package com.development.projetoes.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CadastroUserEventoException extends RuntimeException {

	private static final long serialVersionUID = 5584796611141683251L;

	private final HttpStatus httpStatus;

	public CadastroUserEventoException(final String mensagem, final HttpStatus httpStatus) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}

}
