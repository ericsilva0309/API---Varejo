package com.varejo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
