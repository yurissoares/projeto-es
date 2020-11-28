package com.development.projetoes.dto;

import com.development.projetoes.entity.EventoEntity;
import com.development.projetoes.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CadastroUserEventoDto {

	private Long userEventoId;

	@NotNull(message = "Informe um id de evento.")
	private EventoEntity evento;

	@NotNull(message = "Informe um id de usuário.")
	private UserEntity user;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtInscricao;

	@NotNull
	private Boolean isConcluido;
}
