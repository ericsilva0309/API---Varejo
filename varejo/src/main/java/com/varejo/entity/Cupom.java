package com.varejo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.varejo.enums.TipoCupom;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Cupom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String codigoCupom;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	
	private BigDecimal valorMinimo;
	
	private BigDecimal  valorDesconto; 
	
	private LocalDate dataExpiracao;
	
	//private Caterogia categoria; verificar se a Aninha vai fazer essa classe. 
	
	@Enumerated(EnumType.STRING)
	private TipoCupom tipoCupom;

	
	public String getCodigoCupom() {
		return codigoCupom;
	}

	public void setCodigoCupom(String codigoCupom) {
		this.codigoCupom = codigoCupom;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public LocalDate getDataExpiração() {
		return dataExpiracao;
	}

	public void setDataExpiração(LocalDate dataExpiração) {
		this.dataExpiracao = dataExpiração;
	}

	public TipoCupom getTipoCupom() {
		return tipoCupom;
	}

	public void setTipoCupom(TipoCupom tipoCupom) {
		this.tipoCupom = tipoCupom;
	}

	public Pedido getIdPedido() {
		return pedido;
	}

	public void setIdPedido(Pedido idPedido) {
		this.pedido = idPedido;
	}
	

	
}
