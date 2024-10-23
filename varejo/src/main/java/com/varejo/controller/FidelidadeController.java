package com.varejo.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.ClienteFidelidade;
import com.varejo.service.FidelidadeService;


//Eric

@RestController
@RequestMapping("/fidelidade")
public class FidelidadeController {

	@Autowired
	private FidelidadeService fidelidadeService;
	
	@PostMapping("{clienteId}/comprar")
	public ClienteFidelidade registrarCompra(@PathVariable Long clienteId, @RequestBody BigDecimal valorCompra) {
		return fidelidadeService.registrarCompra(clienteId, valorCompra);
	}
	
	@GetMapping("{clienteId}/desconto")
	public BigDecimal calcularDesconto(@PathVariable Long clienteId) {
		return fidelidadeService.calcularDesconto(clienteId);
	}
	
	@GetMapping("/nivelPorEmail")
	public int consultarNivelFidelidade(@RequestParam String email) {
		return fidelidadeService.consultarNivelFidelidadePorEmail(email);
	}
}
