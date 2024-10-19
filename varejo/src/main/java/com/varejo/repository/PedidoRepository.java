package com.varejo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
