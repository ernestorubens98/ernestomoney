package com.ernesto.moneyapi.repository;

import com.ernesto.moneyapi.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
  
}
