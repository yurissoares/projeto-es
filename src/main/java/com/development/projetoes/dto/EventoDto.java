package com.development.projetoes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.development.projetoes.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EventoDto {

	private Long eventoId;

	@NotBlank(message = "Informe um nome.")
	private String nome;

	//@NotBlank(message = "Informe uma data.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtCriacao;

	//@NotBlank(message = "Informe uma data.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtEncerramento;

	@NotBlank(message = "Informe uma informação sobre o evento.")
	private String informacoes;

	@NotNull(message = "Informe um id de usuário.")
	private UserEntity user;
	
}
