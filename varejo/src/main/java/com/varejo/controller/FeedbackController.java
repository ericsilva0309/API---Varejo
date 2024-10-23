package com.varejo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.varejo.entity.Feedback;
import com.varejo.service.FeedbackService;

import jakarta.validation.Valid;

//Parte Indiviidual: João Vitor 


@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback inserir(@Valid @RequestBody Feedback f) {
        return service.inserir(f);
    }

    @PutMapping("{id}")
    public ResponseEntity<Feedback> alterarServico(@PathVariable Long id, @Valid @RequestBody Feedback f) {
        Feedback atualizado = service.alterarServico(id, f);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/varios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Feedback> inserirVarios(@RequestBody List<Feedback> servicos) {
        return service.inserirVarios(servicos);
    }

    @GetMapping
    public List<Feedback> listar() {
        return service.listar();
       
    }

    @GetMapping("{id}")
    public ResponseEntity<Feedback> buscarPorId(@PathVariable Long id) {
        Feedback feedback = service.buscarPorId(id);
        if (feedback != null) {
            return ResponseEntity.ok(feedback);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (service.apagar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

