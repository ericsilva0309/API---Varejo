package com.varejo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.Cliente;
import com.varejo.entity.ClienteFidelidade;
import com.varejo.service.ClienteService;
import com.varejo.service.FidelidadeService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	
	@Autowired
	private FidelidadeService fidelidadeService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes(){
		List<Cliente> clientes = clienteService.listar();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping
	public List<ClienteFidelidade> listarClientesComNivelFidelidade(){
		List<ClienteFidelidade> clientes = fidelidadeService.listarClientesFidelidade();
		
		// Aqui pode retornar a lista diretamente,
        // pois cada ClienteFidelidade já contém o nível como um enum
		return clientes;
	}
	
	@PostMapping
	public ResponseEntity<Cliente> inserirCliente(@PathVariable Cliente cliente) throws MessagingException{
		Cliente novoCliente = clienteService.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> editarCliente(@PathVariable Long id, @RequestBody Cliente cliente){
		Cliente clienteAtualizado = clienteService.editar(id, cliente);
		return ResponseEntity.ok(clienteAtualizado);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> excluirCliente(@PathVariable Long id){
		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
}
