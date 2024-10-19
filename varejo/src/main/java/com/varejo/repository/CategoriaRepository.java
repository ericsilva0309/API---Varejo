package com.varejo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
