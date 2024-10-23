package com.varejo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Cliente;
import com.varejo.entity.ClienteFidelidade;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Optional<Cliente> findById(Long id);
	Optional<Cliente> findByEmail(String email);
}
