package com.varejo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClienteFidelidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long clienteId;
	
	// BigDecimal é uma classe usada para representar números decimais com alta precisão,
	// muito útil quando lidamos com valores monetários para evitar erros de arredondamento
	// que podem ocorrer com tipos como float ou double.
	private BigDecimal totalGasto;
	
	 // Nível de fidelidade do cliente: 1 (Bronze), 2 (Prata), 3 (Ouro)
	private int nivelFidelidade; 
	
	public ClienteFidelidade() {}

    // Construtor personalizado para criar um novo registro de ClienteFidelidade
    public ClienteFidelidade(Long clienteId, BigDecimal totalGasto, int nivelFidelidade) {
        this.clienteId = clienteId;
        this.totalGasto = totalGasto;
        this.nivelFidelidade = nivelFidelidade;
        
        
        
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		clienteId = clienteId;
	}

	public BigDecimal getTotalGasto() {
		return totalGasto;
	}

	public void setTotalGasto(BigDecimal totalGasto) {
		this.totalGasto = totalGasto;
	}

	public int getNivelFidelidade() {
		return nivelFidelidade;
	}

	public void setNivelFidelidade(int nivelFidelidade) {
		this.nivelFidelidade = nivelFidelidade;
	}
	
	//método para adicionar o valor de uma nova compra ao total gasto
	//pelo cliente
	public void adicionarCompra(BigDecimal valorCompra) {
		//atualiza o total gasto somando o valor da nova compra
		this.totalGasto = this.totalGasto.add(valorCompra);
		
		//depois de atualizar recalcula o nível de fidelidade do cliente
		atualizarNivelFidelidade();
	}
	
	
	//metodo privado para atualizar o nivel de fidelidade do cliente
	private void atualizarNivelFidelidade() {
		 // O método compareTo() retorna:
	    // - 1 se o valor de totalGasto for maior que o valor comparado,
	    // - 0 se forem iguais,
	    // - -1 se for menor.
	    
	    // Neste caso, estamos verificando se totalGasto é maior que 500.
	    // Se o retorno de compareTo for maior que 0, isso significa que 
		// totalGasto é maior que 500.
		//se o total for maior que 500, o cliente alcança o nível ouro
		if(this.totalGasto.compareTo(new BigDecimal("500")) > 0) {
			this.nivelFidelidade = 3; //nivel ouro
		}
		else if(this.totalGasto.compareTo(new BigDecimal("250")) > 0){
			this.nivelFidelidade = 2; // novel prata
		}
		else {
			this.nivelFidelidade = 1; //nivel bronze
		}
	}
}
