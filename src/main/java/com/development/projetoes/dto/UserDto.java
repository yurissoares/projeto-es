package com.development.projetoes.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.development.projetoes.entity.EventoEntity;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto {

	private Long userId;

	@NotBlank(message = "Informe um nome.")
	private String nome;
	
	@Email
	@NotBlank(message = "Informe um email.")
	private String email;
	
	@NotBlank(message = "Informe uma senha.")
	private String senha;

	@NotBlank(message = "Informe um CPF.")
	@CPF(message = "Informe um CPF válido.")
	private String cpf;

	@NotNull
	private Boolean isAdmin;
}
