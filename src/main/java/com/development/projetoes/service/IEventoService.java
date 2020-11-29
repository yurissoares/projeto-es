package com.development.projetoes.service;

import java.util.List;

import com.development.projetoes.dto.EventoDto;

public interface IEventoService {

//	public Boolean atualizar(final EventoDto evento);

	public Boolean excluir(final Long eventoId);

	public List<EventoDto> listar();

	public EventoDto consultar(final Long eventoId);

	public Boolean cadastrar(final EventoDto evento);
}
