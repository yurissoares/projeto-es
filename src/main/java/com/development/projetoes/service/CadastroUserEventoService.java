package com.development.projetoes.service;

import com.development.projetoes.dto.CadastroUserEventoDto;
import com.development.projetoes.entity.CadastroUserEventoEntity;
import com.development.projetoes.exception.CadastroUserEventoException;
import com.development.projetoes.repository.ICadastroUserEventoRepository;
import com.development.projetoes.repository.IEventoRepository;
import com.development.projetoes.repository.IUserRepository;
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
    private IUserRepository userRepository;

    @Autowired
    private IEventoRepository eventoRepository;

    @Autowired
    public CadastroUserEventoService(ICadastroUserEventoRepository cadastroUserEventoRepository) {
        this.mapper = new ModelMapper();
        this.cadastroUserEventoRepository = cadastroUserEventoRepository;
    }

    private void verificaSeUserExiste(Long userId) {
        if(!this.userRepository.findById(userId).isPresent()) {
            throw new CadastroUserEventoException("User não existe.", HttpStatus.BAD_REQUEST);
        }
    }

    private void verificaSeEventoExiste(Long eventoId) {
        if(!this.eventoRepository.findById(eventoId).isPresent()) {
            throw new CadastroUserEventoException("Evento não existe.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Boolean atualizar(CadastroUserEventoDto cadastroUserEvento) {
        try {
            this.consultar(cadastroUserEvento.getUserEventoId());
            this.verificaSeUserExiste(cadastroUserEvento.getUser().getUserId());
            this.verificaSeEventoExiste(cadastroUserEvento.getEvento().getEventoId());

            CadastroUserEventoEntity cadastroUserEventoEntityAtualizada =
                    this.mapper.map(cadastroUserEvento, CadastroUserEventoEntity.class);
            this.cadastroUserEventoRepository.save(cadastroUserEventoEntityAtualizada);
            return Boolean.TRUE;
        } catch (CadastroUserEventoException m) {
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
        } catch (CadastroUserEventoException m) {
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
            throw new CadastroUserEventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
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
            throw new CadastroUserEventoException("Cadastro não encontrado", HttpStatus.NOT_FOUND);
        } catch (CadastroUserEventoException m) {
            throw m;
        } catch (Exception e) {
            throw new CadastroUserEventoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean cadastrar(CadastroUserEventoDto cadastroUserEvento) {
        try {
            this.verificaSeUserExiste(cadastroUserEvento.getUser().getUserId());
            this.verificaSeEventoExiste(cadastroUserEvento.getEvento().getEventoId());

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
