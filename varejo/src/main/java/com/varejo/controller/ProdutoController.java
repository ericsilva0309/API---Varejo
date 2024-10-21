package com.varejo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.Produto;
import com.varejo.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	
	@Autowired
	private ProdutoService service;
	
	@PostMapping
	public ResponseEntity<Produto> inserirProduto(@RequestBody Produto produto){
		Produto novoProduto = service.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
	}
}
