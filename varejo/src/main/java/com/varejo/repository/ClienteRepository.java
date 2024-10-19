package com.varejo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
