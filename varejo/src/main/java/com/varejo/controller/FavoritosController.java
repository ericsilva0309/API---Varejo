package com.varejo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.Cliente;
import com.varejo.entity.Favoritos;
import com.varejo.entity.Produto;
import com.varejo.service.FavoritosService;

//Parte Indiviidual: Ana Carolina


@RestController
@RequestMapping("/favoritos")
public class FavoritosController {
	
	 @Autowired
	 private FavoritosService favoritosService;
	 
	 //Adicionar Favoritos
	 
	 @PostMapping("/adicionar")
	 public ResponseEntity <Favoritos> adicionarFavorito(@RequestParam Long clienteId, @RequestParam Long produtoId) {
		 Cliente cliente = new Cliente();
		 cliente.setId(clienteId);             
		 Produto produto = new Produto(); 
		 produto.setId(produtoId);
		 
		 Favoritos favorito = favoritosService.adicionarFavoritos(cliente, produto);
		 return ResponseEntity.ok(favorito);
	 }
		 
	 //Deletar Favoritos
	 
	 @DeleteMapping ("/{id}")
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
	 
	 //Atualizar Favoritos
	 
	 @PutMapping("/atualizar/{id}")
	 public ResponseEntity<Favoritos> atualizarFavorito(
	         @PathVariable Long id,
	         @RequestParam Long produtoId )
	 
	 {
	     // Busca o favorito existente
	     Favoritos favoritoExistente = favoritosService.buscarFavoritoPorId(id);
	     if (favoritoExistente == null) {
	         return ResponseEntity.notFound().build();
	     }

	     // Atualiza o produto no favorito
	     Produto novoProduto = new Produto();
	     novoProduto.setId(produtoId);
	     favoritoExistente.setProduto(novoProduto);

	     // Atualiza o favorito no banco
	     Favoritos favoritoAtualizado = favoritosService.atualizarFavorito(favoritoExistente);
	     return ResponseEntity.ok(favoritoAtualizado);
	 }
}