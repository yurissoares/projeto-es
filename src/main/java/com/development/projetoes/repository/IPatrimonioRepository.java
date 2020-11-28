package com.development.projetoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.development.projetoes.entity.PatrimonioEntity;

@Repository
public interface IPatrimonioRepository extends JpaRepository<PatrimonioEntity, Long> {

}
