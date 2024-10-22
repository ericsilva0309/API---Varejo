package com.varejo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Favoritos {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

	private LocalDateTime dataAdicionado;


    // Construtores

    public Favoritos() {
        this.dataAdicionado = LocalDateTime.now();  // Data atual ao adicionar um favorito
    }

    public Favoritos(Cliente cliente, Pedido pedido) {
        this.cliente = cliente;
        this.pedido = pedido;
        this.dataAdicionado = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente Cliente) {
        this.cliente = Cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido Pedido) {
        this.pedido = Pedido;
    }

    public LocalDateTime getDataAdicionado() {
        return dataAdicionado;
    }

    public void setDataAdicionado(LocalDateTime dataAdicionado) {
        this.dataAdicionado = dataAdicionado;
    }
}
