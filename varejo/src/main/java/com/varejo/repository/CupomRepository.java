package com.varejo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Cupom;

public interface CupomRepository extends JpaRepository<Cupom, String> {
   
	
}