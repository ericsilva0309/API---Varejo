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
	private final FidelidadeService fidelidadeService;

	@Autowired
	public ClienteController(ClienteService clienteService, FidelidadeService fidelidadeService) {
		this.clienteService = clienteService;
		this.fidelidadeService = fidelidadeService;
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes() {
		List<Cliente> clientes = clienteService.listar();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/fidelidade")
	public ResponseEntity<List<ClienteFidelidade>> listarClientesComNivelFidelidade() {
		List<ClienteFidelidade> clientes = fidelidadeService.listarClientesFidelidade();
		return ResponseEntity.ok(clientes);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> inserirCliente(@RequestBody Cliente cliente) throws MessagingException {
		Cliente novoCliente = clienteService.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> editarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente clienteAtualizado = clienteService.editar(id, cliente);
		return ResponseEntity.ok(clienteAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
