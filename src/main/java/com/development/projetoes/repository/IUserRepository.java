package com.development.projetoes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.development.projetoes.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByNome(String nome);

	public boolean existsByEmail(String email);

	public boolean existsByCpf(String cpf);

	public Optional<UserEntity> findByEmail(String username);

	public Optional<UserEntity> findByCpf(String cpf);
	
}
