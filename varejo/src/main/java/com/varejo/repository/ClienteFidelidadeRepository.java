package com.varejo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.ClienteFidelidade;

public interface ClienteFidelidadeRepository extends JpaRepository<ClienteFidelidade, Long> {

    Optional<ClienteFidelidade> findByClienteId(Long clienteId);
}
