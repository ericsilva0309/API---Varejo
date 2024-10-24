package com.varejo.service;

//Parte Indiviidual: Marina Mayumi

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Cupom;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.CupomRepository;

@Service
public class CupomService {

    private final CupomRepository cupomRepository;

    /**
     * Construtor que injeta o repositório de cupons.
     * 
     * @param cupomRepository Repositório usado para realizar operações com cupons no banco de dados.
     * 
     * O @Autowired é necessário caso tenha mais de um construtor, pois ele indicaria ao Spring qual construtor
     * usar para injeção de dependências. Como há apenas um construtor aqui, o @Autowired é opcional.
     */
    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    /**
     * Lista todos os cupons disponíveis no sistema.
     * 
     * @return Lista de todos os cupons cadastrados.
     */
    public List<Cupom> listarTodos() {
        return cupomRepository.findAll();
    }

    /**
     * Busca um cupom específico pelo código.
     * 
     * @param codigo Código do cupom a ser buscado.
     * @return Optional que pode conter o cupom encontrado.
     */
    public Optional<Cupom> buscarPorCodigo(Long codigo) {
        return cupomRepository.findByIdCupom(codigo);
    }

    /**
     * Cria um novo cupom no sistema.
     * 
     * @param cupom Cupom a ser criado.
     * @return Cupom criado e salvo no banco de dados.
     */
    public Cupom criarCupom(Cupom cupom) {
        return cupomRepository.save(cupom);
    }

    /**
     * Atualiza um cupom existente com base no código informado.
     * 
     * @param codigo Código do cupom a ser atualizado.
     * @param cupomAtualizado Cupom contendo os novos dados para atualização.
     * @return Cupom atualizado e salvo no banco de dados.
     * @throws ResourceNotFoundException Se o cupom com o código informado não for encontrado.
     */
    public Cupom atualizarCupom(Long codigo, Cupom cupomAtualizado) {
        Optional<Cupom> cupomExistente = cupomRepository.findById(codigo);
        if (cupomExistente.isPresent()) {
            Cupom cupom = cupomExistente.get();
            cupom.setValorMinimo(cupomAtualizado.getValorMinimo());
            cupom.setValorDesconto(cupomAtualizado.getValorDesconto());
            cupom.setDataExpiração(cupomAtualizado.getDataExpiração());
            cupom.setTipoCupom(cupomAtualizado.getTipoCupom());
            cupom.setIdPedido(cupomAtualizado.getIdPedido());
            return cupomRepository.save(cupom);
        } else {
            throw new ResourceNotFoundException("Nenhum cupom foi encontrado.");
        }
    }

    /**
     * Deleta um cupom com base no código informado.
     * 
     * @param codigo Código do cupom a ser deletado.
     * @return true se o cupom foi deletado com sucesso, false caso contrário.
     * @throws ResourceNotFoundException Se o cupom com o código informado não for encontrado.
     */
    public boolean deletarCupom(Long codigo) {
        Optional<Cupom> cupomExistente = cupomRepository.findById(codigo);
        if (cupomExistente.isPresent()) {
            cupomRepository.deleteById(codigo);
            return true;
        } else {
            throw new ResourceNotFoundException("Cupom com código " + codigo + " não encontrado.");
        }
    }
}

// listarTodos(ok)
// buscarPorCodigo(ok)
// criarCupom(ok)
// atualizarCupom(ok)
// deletarCupom(ok)
