package com.varejo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.varejo.entity.Cupom;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.CupomRepository;

	@Service
	public class CupomService {
	
	private CupomRepository cupomRepository;
	
	@Autowired
	public CupomService(CupomRepository cupomRepository) {
		this.cupomRepository=cupomRepository;
		
	}

	   public List<Cupom> listarTodos() {
	        return cupomRepository.findAll();
	    }
	   
	   public Optional<Cupom> buscarPorCodigo(Long codigo) {
	        return cupomRepository.findByIdCupom(codigo);
	    }
	   
	   public Cupom criarCupom(Cupom cupom) {
		   
		   return cupomRepository.save(cupom);
	   }
	   
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
	//buscarPorCodigo(ok);
	//criarCupom(ok);
	//atualizarCupom(ok)
	//deletarCupom(ok);
	
	
	
	

