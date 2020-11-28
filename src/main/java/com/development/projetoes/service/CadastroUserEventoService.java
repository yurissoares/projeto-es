package com.development.projetoes.service;

import com.development.projetoes.dto.CadastroUserEventoDto;
import com.development.projetoes.entity.CadastroUserEventoEntity;
import com.development.projetoes.exception.CadastroUserEventoException;
import com.development.projetoes.exception.EventoException;
import com.development.projetoes.exception.UserException;
import com.development.projetoes.repository.ICadastroUserEventoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroUserEventoService implements ICadastroUserEventoService {

    private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";

    private ICadastroUserEventoRepository cadastroUserEventoRepository;
    private ModelMapper mapper;

    @Autowired
    public CadastroUserEventoService(ICadastroUserEventoRepository cadastroUserEventoRepository) {
        this.mapper = new ModelMapper();
        this.cadastroUserEventoRepository = cadastroUserEventoRepository;
    }

    @Override
    public Boolean atualizar(CadastroUserEventoDto cadastroUserEvento) {
        try {
            this.consultar(cadastroUserEvento.getUserEventoId());

            CadastroUserEventoEntity cadastroUserEventoEntityAtualizada =
                    this.mapper.map(cadastroUserEvento, CadastroUserEventoEntity.class);
            this.cadastroUserEventoRepository.save(cadastroUserEventoEntityAtualizada);
            return Boolean.TRUE;
        } catch (UserException m) {
            throw m;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean excluir(Long cadastroUserEventoId) {
        try {
            this.consultar(cadastroUserEventoId);
            this.cadastroUserEventoRepository.deleteById(cadastroUserEventoId);
            return Boolean.TRUE;
        } catch (EventoException m) {
            throw m;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CadastroUserEventoDto> listar() {
        try {
            List<CadastroUserEventoDto> cadastroUserEventoDto =
                    this.mapper.map(this.cadastroUserEventoRepository.findAll(),
                            new TypeToken<List<CadastroUserEventoDto>>() {}.getType());

            return cadastroUserEventoDto;
        } catch (Exception e) {
            throw new EventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CadastroUserEventoDto consultar(Long cadastroUserEventoId) {
        try {
            Optional<CadastroUserEventoEntity> cadastroUserEventoOptional =
                    this.cadastroUserEventoRepository.findById(cadastroUserEventoId);
            if (cadastroUserEventoOptional.isPresent()) {
                return this.mapper.map(cadastroUserEventoOptional.get(), CadastroUserEventoDto.class);
            }
            throw new EventoException("Cadastro não encontrado", HttpStatus.NOT_FOUND);
        } catch (EventoException m) {
            throw m;
        } catch (Exception e) {
            throw new EventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean cadastrar(CadastroUserEventoDto cadastroUserEvento) {
        try {
            CadastroUserEventoEntity cadastroUserEventoEntity = this.mapper.map(cadastroUserEvento, CadastroUserEventoEntity.class);
            this.cadastroUserEventoRepository.save(cadastroUserEventoEntity);
            return Boolean.TRUE;
        } catch (CadastroUserEventoException m) {
            throw m;
        } catch (Exception e) {
            throw new CadastroUserEventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
