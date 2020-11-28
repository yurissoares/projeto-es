package com.development.projetoes.service;

import com.development.projetoes.dto.CadastroUserEventoDto;

import java.util.List;

public interface ICadastroUserEventoService {

	public Boolean atualizar(final CadastroUserEventoDto cadastroUserEventoDto);

	public Boolean excluir(final Long cadastroUserEventoId);

	public List<CadastroUserEventoDto> listar();

	public CadastroUserEventoDto consultar(final Long cadastroUserEventoId);

	public Boolean cadastrar(final CadastroUserEventoDto cadastroUserEventoDto);
}
