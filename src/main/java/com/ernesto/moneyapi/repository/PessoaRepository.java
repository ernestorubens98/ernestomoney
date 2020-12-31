package com.ernesto.moneyapi.repository;

import com.ernesto.moneyapi.model.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
  
}
