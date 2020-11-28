package com.development.projetoes.service;

import java.util.List;

import com.development.projetoes.dto.UserDto;

public interface IUserService {

	public Boolean atualizar(final UserDto user);

	public Boolean excluir(final Long userId);

	public List<UserDto> listar();

	public UserDto consultar(final Long userId);

	public Boolean cadastrar(final UserDto user);
}
