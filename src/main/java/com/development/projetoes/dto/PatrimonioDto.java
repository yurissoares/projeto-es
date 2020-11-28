package com.development.projetoes.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PatrimonioDto {

	private Long patrimonioId;
	
	@NotBlank(message = "Informe o nome do patrimonio.")
	private String nome;
	
	private Long marcaId;
	
	private String descricao;
	
	private String numTombo;
}
