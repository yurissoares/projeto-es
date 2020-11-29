package com.development.projetoes.service;

import java.util.List;
import java.util.Optional;

import com.development.projetoes.dto.EventoDto;
import com.development.projetoes.exception.EventoException;
import com.development.projetoes.repository.IEventoRepository;
import com.development.projetoes.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.development.projetoes.entity.EventoEntity;

@Service
public class EventoService implements IEventoService {

	private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
	
	private IEventoRepository eventoRepository;
	private ModelMapper mapper;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	public EventoService(IEventoRepository eventoRepository) {
		this.mapper = new ModelMapper();
		this.eventoRepository = eventoRepository;
	}
	
	private void verificarNomeExistente(String nome) {
		if(this.eventoRepository.existsByNome(nome)) {
			throw new EventoException("Esse nome de evento já existe.", HttpStatus.BAD_REQUEST);
		}
	}

	private void verificarSeUserEhAdm(Long userId) {
		if(!this.userRepository.findById(userId).get().getIsAdmin()) {
			throw new EventoException("Esse user não é ADM.", HttpStatus.BAD_REQUEST);
		}
	}

	private void verificaSeUserExiste(Long userId) {
		if(!this.userRepository.findById(userId).isPresent()) {
			throw new EventoException("User não existe.", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public Boolean atualizar(EventoDto evento) {
		try {
			this.consultar(evento.getEventoId());
			this.verificaSeUserExiste(evento.getUser().getUserId());
			this.verificarSeUserEhAdm(evento.getUser().getUserId());
			
			Optional<EventoEntity> eventoOptional = this.eventoRepository.findByNome(evento.getNome());
			if(eventoOptional.isPresent()) {
				if(eventoOptional.get().getEventoId() != evento.getEventoId()) {
					throw new EventoException("Esse nome de evento já existe.", HttpStatus.BAD_REQUEST);
				}
			}
			
			EventoEntity eventoEntityAtualizada = this.mapper.map(evento, EventoEntity.class);
			this.eventoRepository.save(eventoEntityAtualizada);
			return Boolean.TRUE;
		} catch (EventoException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean excluir(Long eventoId) {
		try {
			this.consultar(eventoId);
			this.eventoRepository.deleteById(eventoId);
			return Boolean.TRUE;
		} catch (EventoException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public EventoDto consultar(Long eventoId) {
		try {
			Optional<EventoEntity> eventoOptional = this.eventoRepository.findById(eventoId);
			if (eventoOptional.isPresent()) {
				return this.mapper.map(eventoOptional.get(), EventoDto.class);
			}
			throw new EventoException("Evento não encontrado", HttpStatus.NOT_FOUND);
		} catch (EventoException m) {
			throw m;
		} catch (Exception e) {
			throw new EventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<EventoDto> listar() {
		try {
			List<EventoDto> eventoDto = this.mapper.map(this.eventoRepository.findAll(), new TypeToken<List<EventoDto>>() {}.getType());

			return eventoDto;
		} catch (Exception e) {
			throw new EventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean cadastrar(EventoDto evento) {
		try {
			this.verificarNomeExistente(evento.getNome());
			this.verificaSeUserExiste(evento.getUser().getUserId());
			this.verificarSeUserEhAdm(evento.getUser().getUserId());

			EventoEntity eventoEntity = this.mapper.map(evento, EventoEntity.class);
			this.eventoRepository.save(eventoEntity);
			return Boolean.TRUE;
		} catch (EventoException m) {
			throw m;
		} catch (Exception e) {
			throw new EventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
