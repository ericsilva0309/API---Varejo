package com.varejo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Cliente;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.ClienteRepository;

import jakarta.mail.MessagingException;

@Service
public class ClienteService {

	private final ClienteRepository repository;
	
	@Autowired
	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public List<Cliente> listar(){
		return repository.findAll();
	}
	
	public Cliente save(Cliente cliente) throws MessagingException{
		return repository.save(cliente);
	}
	
	public Cliente editar(Long id, Cliente cliente) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Cliente não encontrado com ID: "+id);
		}
		cliente.setId(id);
		return repository.save(cliente);
	}
	
	public void excluir(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Cliente não encontrado com ID: "+id);
		} 
			repository.deleteById(id);
	}
}
