package com.development.projetoes.repository;

import com.development.projetoes.entity.CadastroUserEventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICadastroUserEventoRepository extends JpaRepository<CadastroUserEventoEntity, Long> {

}
