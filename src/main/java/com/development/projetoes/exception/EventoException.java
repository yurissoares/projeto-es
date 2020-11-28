package com.development.projetoes.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class EventoException extends RuntimeException {
	
	private static final long serialVersionUID = 5584796611141683251L;
	
	private final HttpStatus httpStatus;
	
	public EventoException(final String mensagem, final HttpStatus httpStatus) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}

}
