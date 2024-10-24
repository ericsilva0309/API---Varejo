package com.varejo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Produto;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.ProdutoRepository;

import jakarta.mail.MessagingException;

@Service
public class ProdutoService {

    private final ProdutoRepository repository; // Repositório de produtos
    
    @Autowired
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository; // Injeção de dependência do repositório
    }
    
    /**
     * Lista todos os produtos.
     * @return Uma lista de produtos.
     */
    public List<Produto> listar() {
        return repository.findAll(); // Retorna todos os produtos do repositório
    }
    
    /**
     * Salva um novo produto no repositório.
     * @param produto O objeto Produto a ser salvo.
     * @return O produto salvo.
     * @throws MessagingException Se ocorrer um erro ao enviar um e-mail relacionado ao produto.
     */
    public Produto save(Produto produto) throws MessagingException {
        return repository.save(produto); // Salva e retorna o produto
    }
    
    /**
     * Edita um produto existente, atualizando seus dados.
     * @param id O ID do produto a ser editado.
     * @param produto O objeto Produto com os novos dados.
     * @return O produto atualizado.
     * @throws ResourceNotFoundException Se o produto não for encontrado.
     */
    public Produto editar(Long id, Produto produto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id); // Lança exceção se o produto não existir
        }
        produto.setId(id); // Define o ID do produto a ser atualizado
        return repository.save(produto); // Salva e retorna o produto atualizado
    }
    
    /**
     * Exclui um produto pelo ID.
     * @param id O ID do produto a ser excluído.
     * @throws ResourceNotFoundException Se o produto não for encontrado.
     */
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
        } 
        repository.deleteById(id);
    }
}
