 


package com.varejo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Feedback;
import com.varejo.repository.FeedbackRepository;

import jakarta.validation.Valid;


//Parte Indiviidual: João Vitor 

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    public Feedback inserir(@Valid Feedback f) {
        return repository.save(f);
    }

    public Feedback alterarServico(Long id, @Valid Feedback f) {
        if (repository.existsById(id)) {
            f.setId(id);
            return repository.save(f);
        }
        return null;
    }

    public List<Feedback> inserirVarios(List<Feedback> servicos) {
        return repository.saveAll(servicos);
    }

    public List<Feedback> listar() {
        return repository.findAll();
    }
    
    public Feedback buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean apagar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
