package com.varejo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Cupom;

public interface CupomRepository extends JpaRepository<Cupom, Long> {
   
	Optional<Cupom> findByIdCupom(Long idCupom);
}
