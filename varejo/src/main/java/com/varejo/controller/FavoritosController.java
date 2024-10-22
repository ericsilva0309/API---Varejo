package com.varejo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.Cliente;
import com.varejo.entity.Favoritos;
import com.varejo.entity.Pedido;
import com.varejo.service.FavoritosService;

@RestController
@RequestMapping("/favoritos")
public class FavoritosController {
	
	 @Autowired
	 private FavoritosService favoritosService;
	 
	 //Adicionar Favoritos
	 
	 @PostMapping("/adicionar")
	 public ResponseEntity <Favoritos> adicionarFavorito(@RequestParam Long clienteId, @RequestParam Long pedidoId) {
		 Cliente cliente = new Cliente();
		 cliente.setId(clienteId);             
		 Pedido pedido = new Pedido(); 
		 pedido.setId(pedidoId);
		 
		 Favoritos favorito = favoritosService.adicionarFavoritos(cliente, pedido);
		 return ResponseEntity.ok(favorito);
	 }
		 
	 //Deletar Favoritos
	 
	 @DeleteMapping
	 public ResponseEntity<String> removerFavorito(@PathVariable Long id) {
		 favoritosService.removerFavorito(id);         
		 return ResponseEntity.ok("Item removido com sucesso!");
	 }
	 
	 //Listar Favoritos
	 
	 @GetMapping("/cliente/{clienteId}")
	 public ResponseEntity<List<Favoritos>> listarFavoritos(@PathVariable Long clienteId) {
		 Cliente cliente = new Cliente();
		 cliente.setId(clienteId);              
		 List<Favoritos> favoritos = favoritosService.listarFavoritosPorCliente(cliente);
		 return ResponseEntity.ok(favoritos);
		 }
}