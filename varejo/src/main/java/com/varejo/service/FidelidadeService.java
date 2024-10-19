package com.varejo.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.ClienteFidelidade;
import com.varejo.repository.ClienteFidelidadeRepository;

@Service
public class FidelidadeService {

	@Autowired
	private ClienteFidelidadeRepository fidelidadeRepository;
	
	 /**
     * Registra uma compra para um cliente, incrementando o total gasto e atualizando o nível de fidelidade.
     * @param clienteId O ID do cliente.
     * @param valorCompra O valor da compra.
     * @return O objeto ClienteFidelidade atualizado.
     */
	public ClienteFidelidade registrarCompra(Long clienteId, BigDecimal valorCompra) {
	Optional<ClienteFidelidade> optionalFidelidade = fidelidadeRepository.findByClienteId(clienteId);
	
	ClienteFidelidade fidelidade;
	
	//se o cliente já existe, adiciona a compra
	if(optionalFidelidade.isPresent()) {
		fidelidade = optionalFidelidade.get(); //obtem o cliente existente
		fidelidade.adicionarCompra(valorCompra); // adiciona o valor d acompra ao total e atualiza o nivel
		
	}else {
		//se nao existir cria um novo cliente de fidelidade com valor da compra e nivel bronze
		fidelidade = new ClienteFidelidade(clienteId, valorCompra, 1); //1 = bronze
	}
	
	return fidelidadeRepository.save(fidelidade);
	}
	
	
	/**
     * Calcula o desconto para o cliente baseado no seu nível de fidelidade.
     * @param clienteId O ID do cliente.
     * @return O valor do desconto (em porcentagem), como um BigDecimal.
     */
	public BigDecimal calcularDesconto(Long clienteId) {
		Optional<ClienteFidelidade> optionalFidelidade = fidelidadeRepository.findByClienteId(clienteId);
		
		//se o cliente foi encontrado...
		if(optionalFidelidade.isPresent()){
			ClienteFidelidade fidelidade = optionalFidelidade.get();
			
			//calcula o desconto com base no nível de fidelidade
			switch (fidelidade.getNivelFidelidade()) {
			case 3: //nivel ouro
				return new BigDecimal("0.15"); //15% de desconto
			case 2: //nivel prata
				return new BigDecimal("0.10");
			default: //nivel bronze ou qualquer outro
				return new BigDecimal("0.05");
			}
		}
		
		//se nao existir retorna 0% de desconto
		return BigDecimal.ZERO;
	}
}
