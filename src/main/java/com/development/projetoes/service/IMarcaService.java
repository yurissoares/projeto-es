package com.development.projetoes.service;

import java.util.List;

import com.development.projetoes.dto.MarcaDto;

public interface IMarcaService {

	public Boolean atualizar(final MarcaDto marca);

	public Boolean excluir(final Long marcaId);

	public List<MarcaDto> listar();

	public MarcaDto consultar(final Long marcaId);

	public Boolean cadastrar(final MarcaDto marca);
}
