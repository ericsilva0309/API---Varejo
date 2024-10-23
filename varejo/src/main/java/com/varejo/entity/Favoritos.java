package com.varejo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;


//Parte Indiviidual: Ana Carolina

@Entity
@SequenceGenerator(name = "favoritos_seq", sequenceName = "favoritos_seq", allocationSize = 50)
public class Favoritos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favoritos_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "Produto_id", nullable = false)
    private Produto produto;


    public Favoritos(Cliente Cliente, Produto Produto) {
        this.cliente = Cliente;
        this.produto = Produto;

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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto Produto) {
        this.produto = Produto;
    }

}