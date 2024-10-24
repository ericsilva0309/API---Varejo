package com.varejo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Cliente;
import com.varejo.entity.ClienteFidelidade;
import com.varejo.repository.ClienteFidelidadeRepository;
import com.varejo.repository.ClienteRepository;

//Eric

@Service
public class FidelidadeService {

	@Autowired
	private ClienteFidelidadeRepository fidelidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
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
		int nivelAntigo = fidelidade.getNivelFidelidade();
		fidelidade.adicionarCompra(valorCompra); // adiciona o valor d acompra ao total e atualiza o nivel
		
		if(nivelAntigo < fidelidade.getNivelFidelidade()) {
			enviarEmailSubirNivelFidelidade(clienteId, fidelidade);
		}
	}else {
		//se nao existir cria um novo cliente de fidelidade com valor da compra e nivel bronze
		fidelidade = new ClienteFidelidade(clienteId, valorCompra, 1); //1 = bronze
	}
	
	return fidelidadeRepository.save(fidelidade);
	}
	
	
	/**
     * Envia um e-mail ao cliente informando sobre a mudança de nível de fidelidade.
     * @param clienteId O ID do cliente.
     * @param fidelidade O objeto ClienteFidelidade com as informações atualizadas.
     */
	
	private void enviarEmailSubirNivelFidelidade(Long clienteId, ClienteFidelidade fidelidade){
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
		
		String para = cliente.getEmail();
		String assunto = "Parabéns! Você subiu de nível de fidelidade na nossa loja!";
		String texto = String.format(
				"Olá, cliente %d! Parabéns, você agora é nível %d de fidelidade! Total gasto: %s",
				clienteId,
				fidelidade.getNivelFidelidade(),
				fidelidade.getTotalGasto()
				);
		
		emailService.enviarEmail(para, assunto, texto);
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
	
	/**
	 * Consulta o nível de fidelidade de um cliente com base no seu ID.
	 * @param clienteId O ID do cliente.
	 * @return O nível de fidelidade do cliente ou 0 se o cliente não for encontrado.
	 */
	
	public int consultarNivelFidelidadePorEmail(String email) {
	    // Encontra o cliente pelo e-mail
	    Cliente cliente = clienteRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o e-mail: " + email));
	    
	    // Usa o ID do cliente para consultar o nível de fidelidade
	    Optional<ClienteFidelidade> optionalFidelidade = fidelidadeRepository.findByClienteId(cliente.getId());
	    
	    if (optionalFidelidade.isPresent()) {
	        ClienteFidelidade fidelidade = optionalFidelidade.get();
	        // Enviar email com informações do nível de fidelidade
	        clienteService.enviarEmailNivelFidelidade(cliente, 0);
	        
	        return fidelidade.getNivelFidelidade();
	    }
	    
	    return 0; // Retornar 0 se não houver nível de fidelidade
	}

//	private void enviarEmailNivelFidelidade(Long clienteId, ClienteFidelidade fidelidade) {
//	    Cliente cliente = clienteRepository.findById(clienteId)
//	    		.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
//	    
//	    String para = cliente.getEmail();
//	    String assunto = "Consulta de nível de fidelidade";
//	    String texto = String.format(
//	    		"Olá, cliente %d! Seu nível de fidelidade é %s. Total gasto: %s",
//	    		clienteId,
//	    		(fidelidade.getNivelFidelidade()==1?"BRONZE":fidelidade.getNivelFidelidade()==2?"PRATA":"OURO"),
//	    		fidelidade.getTotalGasto()
//	    		);
//	    
//	    emailService.enviarEmail(para, assunto, texto);
//	}
	
	public List<ClienteFidelidade> listarClientesFidelidade() {
        return fidelidadeRepository.findAll();
    }
}
