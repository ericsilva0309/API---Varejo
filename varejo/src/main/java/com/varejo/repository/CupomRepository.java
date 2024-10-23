package com.varejo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Cupom;

//Parte Indiviidual: Marina Mayumi

public interface CupomRepository extends JpaRepository<Cupom, Long> {
   
	Optional<Cupom> findByIdCupom(Long idCupom);
}
