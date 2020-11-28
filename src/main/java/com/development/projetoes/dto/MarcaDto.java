package com.development.projetoes.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MarcaDto {

	private Long marcaId;
	
	@NotBlank(message = "Informe o nome da marca.")
	private String nome;
	
}
