package com.development.projetoes.service;

import java.util.List;
import java.util.Optional;

import com.development.projetoes.dto.UserDto;
import com.development.projetoes.exception.UserException;
import com.development.projetoes.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.development.projetoes.entity.UserEntity;

@Service
public class UserService implements IUserService {

	private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";

	private IUserRepository userRepository;
	private ModelMapper mapper;

	@Autowired
	public UserService(IUserRepository userRepository) {
		this.mapper = new ModelMapper();
		this.userRepository = userRepository;
	}

	public void verificarEmailExistente(String email) {
		if (this.userRepository.existsByEmail(email)) {
			throw new UserException("Esse nome de email já existe.", HttpStatus.BAD_REQUEST);
		}
	}

	public void verificarCpfExistente(String cpf) {
		if (this.userRepository.existsByCpf(cpf)) {
			throw new UserException("Esse cpf já existe.", HttpStatus.BAD_REQUEST);
		}
	}

//	@Override
//	public Boolean atualizar(UserDto user) {
//		try {
//			this.consultar(user.getUserId());
//			Optional<UserEntity> userOptional = this.userRepository.findByEmail(user.getEmail());
//			if (userOptional.isPresent() && userOptional.get().getUserId() != user.getUserId()) {
//				throw new UserException("Esse email já existe.", HttpStatus.BAD_REQUEST);
//			}
//			Optional<UserEntity> userCpfOptional = this.userRepository.findByCpf(user.getCpf());
//			if (userCpfOptional.isPresent() && userCpfOptional.get().getUserId() != user.getUserId()) {
//				throw new UserException("Esse Cpf já existe.", HttpStatus.BAD_REQUEST);
//			}
//			UserEntity userEntityAtualizada = this.mapper.map(user, UserEntity.class);
//			userEntityAtualizada.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
//
//			this.userRepository.save(userEntityAtualizada);
//			return Boolean.TRUE;
//		} catch (UserException m) {
//			throw m;
//		} catch (Exception e) {
//			throw e;
//		}
//	}

	@Override
	public Boolean excluir(Long userId) {
		try {
			this.consultar(userId);
			this.userRepository.deleteById(userId);
			return Boolean.TRUE;
		} catch (UserException m) {
			throw m;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public UserDto consultar(Long userId) {
		try {
			Optional<UserEntity> userOptional = this.userRepository.findById(userId);
			if (userOptional.isPresent()) {
				return this.mapper.map(userOptional.get(), UserDto.class);
			}
			throw new UserException("User não encontrada", HttpStatus.NOT_FOUND);
		} catch (UserException m) {
			throw m;
		} catch (Exception e) {
			throw new UserException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<UserDto> listar() {
		try {
			List<UserDto> userDto = this.mapper.map(this.userRepository.findAll(), new TypeToken<List<UserDto>>() {
			}.getType());

			return userDto;
		} catch (Exception e) {
			throw new UserException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean cadastrar(UserDto user) {
		try {
			this.verificarEmailExistente(user.getEmail());
			this.verificarCpfExistente(user.getCpf());

			UserEntity userEntity = this.mapper.map(user, UserEntity.class);
			userEntity.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));

			this.userRepository.save(userEntity);
			return Boolean.TRUE;
		} catch (UserException m) {
			throw m;
		} catch (Exception e) {
			throw new UserException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
