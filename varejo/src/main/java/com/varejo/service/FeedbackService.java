package com.varejo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Feedback;
import com.varejo.repository.FeedbackRepository;

import jakarta.validation.Valid;

//Parte Indiviual: João Vitor 

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    /**
     * Insere um novo feedback no banco de dados.
     * 
     * @param f O feedback a ser inserido, deve estar validado.
     * @return O objeto Feedback que foi salvo no banco de dados.
     */
    public Feedback inserir(@Valid Feedback f) {
        return repository.save(f);
    }

    /**
     * Altera um feedback existente com base no ID fornecido.
     * 
     * @param id O ID do feedback a ser alterado.
     * @param f O objeto Feedback com os dados atualizados.
     * @return O objeto Feedback atualizado ou null se o ID não existir.
     */
    public Feedback alterarServico(Long id, @Valid Feedback f) {
        if (repository.existsById(id)) {
            f.setId(id); // Define o ID do feedback que será atualizado
            return repository.save(f); // Salva as alterações
        }
        return null; // Retorna null se o feedback não for encontrado
    }

    /**
     * Insere vários feedbacks no banco de dados.
     * 
     * @param servicos A lista de feedbacks a serem inseridos.
     * @return A lista de objetos Feedback que foram salvos no banco de dados.
     */
    public List<Feedback> inserirVarios(List<Feedback> servicos) {
        return repository.saveAll(servicos);
    }

    /**
     * Lista todos os feedbacks armazenados no banco de dados.
     * 
     * @return Uma lista de objetos Feedback.
     */
    public List<Feedback> listar() {
        return repository.findAll();
    }
    
    /**
     * Busca um feedback pelo ID.
     * 
     * @param id O ID do feedback a ser buscado.
     * @return O objeto Feedback correspondente ao ID fornecido ou null se não encontrado.
     */
    public Feedback buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Apaga um feedback com base no ID fornecido.
     * 
     * @param id O ID do feedback a ser apagado.
     * @return true se o feedback foi apagado com sucesso, false se o ID não existir.
     */
    public boolean apagar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id); // Apaga o feedback
            return true; // Retorna true se a exclusão foi bem-sucedida
        }
        return false; // Retorna false se o feedback não for encontrado
    }
}
