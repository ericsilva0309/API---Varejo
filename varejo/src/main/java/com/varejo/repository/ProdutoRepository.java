package com.varejo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	Optional<Produto> findById(Long id);
}
