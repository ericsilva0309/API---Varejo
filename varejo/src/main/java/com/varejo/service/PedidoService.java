package com.varejo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Pedido;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.PedidoRepository;

import jakarta.mail.MessagingException;

@Service
public class PedidoService {

    private final PedidoRepository repository; // Repositório de pedidos
    
    @Autowired
    public PedidoService(PedidoRepository repository) {
        this.repository = repository; // Injeção de dependência do repositório
    }
    
    /**
     * Lista todos os pedidos.
     * @return Uma lista de pedidos.
     */
    public List<Pedido> listar() {
        return repository.findAll(); // Retorna todos os pedidos do repositório
    }
    
    /**
     * Salva um novo pedido no repositório.
     * @param pedido O objeto Pedido a ser salvo.
     * @return O pedido salvo.
     * @throws MessagingException Se ocorrer um erro ao enviar um e-mail relacionado ao pedido.
     */
    public Pedido save(Pedido pedido) throws MessagingException {
        return repository.save(pedido); // Salva e retorna o pedido
    }
    
    /**
     * Edita um pedido existente, atualizando seus dados.
     * @param id O ID do pedido a ser editado.
     * @param pedido O objeto Pedido com os novos dados.
     * @return O pedido atualizado.
     * @throws ResourceNotFoundException Se o pedido não for encontrado.
     */
    public Pedido editar(Long id, Pedido pedido) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id); // Lança exceção se o pedido não existir
        }
        pedido.setId(id); // Define o ID do pedido a ser atualizado
        return repository.save(pedido); // Salva e retorna o pedido atualizado
    }
    
    /**
     * Exclui um pedido pelo ID.
     * @param id O ID do pedido a ser excluído.
     * @throws ResourceNotFoundException Se o pedido não for encontrado.
     */
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id); // Lança exceção se o pedido não existir
        } 
        repository.deleteById(id);
    }
}
