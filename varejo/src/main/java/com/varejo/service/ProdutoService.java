package com.varejo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Produto;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.ProdutoRepository;

import jakarta.mail.MessagingException;

@Service
public class ProdutoService {

	private final ProdutoRepository repository;
	
	@Autowired
	public ProdutoService(ProdutoRepository repository) {
		this.repository = repository;
	}
	
	public List<Produto> listar(){
		return repository.findAll();
	}
	
	public Produto save(Produto produto) throws MessagingException{
		return repository.save(produto);
	}
	
	public Produto editar(Long id, Produto produto) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Produto não encontrado com ID: "+id);
		}
		produto.setId(id);
		return repository.save(produto);
	}
	
	public void excluir(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Produto não encontrado com ID: "+id);
		} 
			repository.deleteById(id);
	}
}
