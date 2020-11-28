package com.development.projetoes.service;

import java.util.List;
import java.util.Optional;

import com.development.projetoes.dto.MarcaDto;
import com.development.projetoes.exception.MarcaException;
import com.development.projetoes.repository.IMarcaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.development.projetoes.entity.MarcaEntity;

@Service
public class MarcaService implements IMarcaService {

	private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
	
	private IMarcaRepository marcaRepository;
	private ModelMapper mapper;
	
	@Autowired
	public MarcaService(IMarcaRepository marcaRepository) {
		this.mapper = new ModelMapper();
		this.marcaRepository = marcaRepository;
	}
	
	public void verificarNomeExistente(String nome) {
		if(this.marcaRepository.existsByNome(nome)) {
			throw new MarcaException("Esse nome de marca já existe.", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public Boolean atualizar(MarcaDto marca) {
		try {
			this.consultar(marca.getMarcaId());
			
			Optional<MarcaEntity> marcaOptional = this.marcaRepository.findByNome(marca.getNome());
			
			if(marcaOptional.isPresent()) {
				if(marcaOptional.get().getMarcaId() != marca.getMarcaId()) {
					throw new MarcaException("Esse nome de marca já existe.", HttpStatus.BAD_REQUEST);				
				}
			}
			
			MarcaEntity marcaEntityAtualizada = this.mapper.map(marca, MarcaEntity.class);
			this.marcaRepository.save(marcaEntityAtualizada);
			return Boolean.TRUE;
		} catch (MarcaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean excluir(Long marcaId) {
		try {
			this.consultar(marcaId);
			this.marcaRepository.deleteById(marcaId);
			return Boolean.TRUE;
		} catch (MarcaException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public MarcaDto consultar(Long marcaId) {
		try {
			Optional<MarcaEntity> marcaOptional = this.marcaRepository.findById(marcaId);
			if (marcaOptional.isPresent()) {
				return this.mapper.map(marcaOptional.get(), MarcaDto.class);
			}
			throw new MarcaException("Marca não encontrada", HttpStatus.NOT_FOUND);
		} catch (MarcaException m) {
			throw m;
		} catch (Exception e) {
			throw new MarcaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<MarcaDto> listar() {
		try {
			List<MarcaDto> marcaDto = this.mapper.map(this.marcaRepository.findAll(), new TypeToken<List<MarcaDto>>() {}.getType());

			return marcaDto;
		} catch (Exception e) {
			throw new MarcaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean cadastrar(MarcaDto marca) {
		try {
			this.verificarNomeExistente(marca.getNome());
			
			MarcaEntity marcaEntity = this.mapper.map(marca, MarcaEntity.class);
			this.marcaRepository.save(marcaEntity);
			return Boolean.TRUE;
		} catch (MarcaException m) {
			throw m;
		} catch (Exception e) {
			throw new MarcaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
