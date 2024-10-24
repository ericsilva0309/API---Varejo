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
import org.springframework.web.bind.annotation.RestController;

import com.varejo.entity.Cupom;
import com.varejo.service.CupomService;


//Parte Indiviidual: Marina Mayumi

@RestController
@RequestMapping("/cupons")
public class CupomController {
    
    @Autowired
    private CupomService cupomService;

    
    @GetMapping
    public List<Cupom> listarCupons() {
        return cupomService.listarTodos();
    }


    @GetMapping("/{codigo}")
    public ResponseEntity<Cupom> buscarCupom(@PathVariable Long codigo) {
        return cupomService.buscarPorCodigo(codigo)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public ResponseEntity<Cupom> criarCupom(@RequestBody Cupom cupom) {
        Cupom novoCupom = cupomService.criarCupom(cupom);
        return ResponseEntity.ok(novoCupom);
    }

    
    @PutMapping("/{codigo}")
    public ResponseEntity<Cupom> atualizarCupom(@PathVariable Long codigo, @RequestBody Cupom cupom) {
        Cupom cupomAtualizado = cupomService.atualizarCupom(codigo, cupom);
        if (cupomAtualizado != null) {
            return ResponseEntity.ok(cupomAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarCupom(@PathVariable Long codigo) {
        boolean deletado = cupomService.deletarCupom(codigo);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
