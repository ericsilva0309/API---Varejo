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

    /**
     * Busca o endereço pelo CEP informado. Primeiro tenta encontrar o CEP no banco de dados,
     * se não encontrar, faz uma consulta à API ViaCEP.
     * 
     * @param cep O CEP a ser pesquisado.
     * @return Um objeto EnderecoResponseDTO contendo os dados do endereço.
     * @throws HttpClientErrorException Se o CEP não for encontrado.
     */
    public EnderecoResponseDTO buscar(String cep) {
        // Remove o traço do CEP, caso exista
        cep.replaceAll("-", "");
        
        // Verifica se o endereço já existe no banco de dados
        var endereco = Optional.ofNullable(repository.findByCep(cep));
        if (endereco.isPresent()) {
            // Se o endereço existe, retorna os dados
            return new EnderecoResponseDTO(endereco.get());
        } else {
            // Se o endereço não está no banco, consulta a API ViaCEP
            RestTemplate rs = new RestTemplate();
            String uri = "https://viacep.com.br/ws/" + cep + "/json/";
            
            // Faz a consulta à API e converte o retorno em um objeto Endereco
            Optional<Endereco> enderecoViaCep = Optional.ofNullable(rs.getForObject(uri, Endereco.class));
            
            // Verifica se o CEP retornado pela API é válido
            if (enderecoViaCep.get().getCep() != null) {
                // Remove o traço do CEP retornado e insere no banco de dados
                String cepSemtraco = enderecoViaCep.get().getCep().replaceAll("-", "");
                enderecoViaCep.get().setCep(cepSemtraco);
                return inserir(enderecoViaCep.get()); // Salva e retorna o endereço
            } else {
                // Lança exceção se o CEP não for encontrado
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
        }
    }

    /**
     * Insere um novo endereço no banco de dados e retorna os dados em um DTO.
     * 
     * @param endereco O endereço a ser inserido.
     * @return Um objeto EnderecoResponseDTO com os dados do endereço salvo.
     */
    private EnderecoResponseDTO inserir(Endereco endereco) {
        // Salva o endereço no banco de dados
        endereco = repository.save(endereco);
        // Retorna os dados do endereço salvo em um DTO
        return new EnderecoResponseDTO(endereco);
    }
}
