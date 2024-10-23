package com.varejo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.varejo.dto.EnderecoResponseDTO;
import com.varejo.entity.Endereco;
import com.varejo.repository.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository repository;
	
	public EnderecoResponseDTO buscar(String cep) {
		cep.replaceAll("-", "");
		var endereco = Optional.ofNullable(repository.findByCep(cep));
		if(endereco.isPresent()) {
			return new EnderecoResponseDTO(endereco.get());
		}else{
			RestTemplate rs = new RestTemplate();
			String uri = "https://viacep.com.br/ws/"+cep+"/json/";
			Optional<Endereco> enderecoViaCep = Optional.ofNullable(rs.getForObject(uri, Endereco.class));
			if(enderecoViaCep.get().getCep() != null) {
				String cepSemtraco = enderecoViaCep.get().getCep().replaceAll("-", "");
				enderecoViaCep.get().setCep(cepSemtraco);
				return inserir(enderecoViaCep.get());
			}else {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND);	
			}
			
		}
	}

	private EnderecoResponseDTO inserir(Endereco endereco) {
		endereco = repository.save(endereco);
		return new EnderecoResponseDTO(endereco);
	}
}
