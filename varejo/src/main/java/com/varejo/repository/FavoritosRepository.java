package com.varejo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varejo.entity.Favoritos;

public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {

}
