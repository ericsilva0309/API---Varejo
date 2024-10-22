package com.varejo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	Endereco findByCep(String cep);

	
}
