package com.development.projetoes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.development.projetoes.entity.EventoEntity;

@Repository
public interface IEventoRepository extends JpaRepository<EventoEntity, Long> {

	public boolean existsByNome(String nome);
	
	public boolean existsById(Long id);

	public Optional<EventoEntity> findByNome(String nome);
}
