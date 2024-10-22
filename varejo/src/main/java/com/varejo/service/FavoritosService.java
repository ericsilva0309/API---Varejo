package com.varejo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Cliente;
import com.varejo.entity.Favoritos;
import com.varejo.entity.Produto;
import com.varejo.repository.FavoritosRepository;

@Service
public class FavoritosService {
	
	@Autowired
	private FavoritosRepository favoritosRepository;
	
	public Favoritos adicionarFavoritos(Cliente cliente, Produto produto ) {
		Favoritos favorito = new Favoritos(cliente, produto);
		return favoritosRepository.save(favorito);
	}
	
	public void removerFavorito(Long id) {
		favoritosRepository.deleteById(id);  
	}
	
	public List<Favoritos> listarFavoritosPorCliente(Cliente cliente) {
		return favoritosRepository.findAll();
	}

}