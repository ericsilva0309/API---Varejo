package com.varejo.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.ClienteFidelidade;
import com.varejo.service.FidelidadeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
}
