package com.varejo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.ClienteFidelidade;
import com.varejo.service.FidelidadeService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private FidelidadeService fidelidadeService;

	@GetMapping
	public List<ClienteFidelidade> listarClientesComNivelFidelidade(){
		List<ClienteFidelidade> clientes = fidelidadeService.listarClientesFidelidade();
		
		// Aqui pode retornar a lista diretamente,
        // pois cada ClienteFidelidade já contém o nível como um enum
		return clientes;
	}
}
