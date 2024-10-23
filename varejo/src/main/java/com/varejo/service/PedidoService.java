package com.varejo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Pedido;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.PedidoRepository;

import jakarta.mail.MessagingException;

@Service
public class PedidoService {

	private final PedidoRepository repository;
	
	@Autowired
	public PedidoService(PedidoRepository repository) {
		this.repository = repository;
	}
	
	public List<Pedido> listar(){
		return repository.findAll();
	}
	
	public Pedido save(Pedido pedido) throws MessagingException{
		return repository.save(pedido);
	}
	
	public Pedido editar(Long id, Pedido pedido) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Pedido não encontrado com ID: "+id);
		}
		pedido.setId(id);
		return repository.save(pedido);
	}
	
	public void excluir(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Pedido não encontrado com ID: "+id);
		} 
			repository.deleteById(id);
	}
}
