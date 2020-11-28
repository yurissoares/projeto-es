package com.development.projetoes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.development.projetoes.entity.MarcaEntity;

@Repository
public interface IMarcaRepository extends JpaRepository<MarcaEntity, Long> {

	public boolean existsByNome(String nome);
	
	public boolean existsById(Long id);

	public Optional<MarcaEntity> findByNome(String nome);
}
