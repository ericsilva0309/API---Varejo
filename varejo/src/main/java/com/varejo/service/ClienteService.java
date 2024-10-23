package com.varejo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.varejo.dto.EnderecoResponseDTO;
import com.varejo.entity.Cliente;
import com.varejo.entity.ClienteFidelidade;
import com.varejo.entity.Endereco;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.ClienteFidelidadeRepository;
import com.varejo.repository.ClienteRepository;

import jakarta.mail.MessagingException;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final EnderecoService enderecoService;
    private final EmailService emailService; // Adicionando EmailService
    private final ClienteFidelidadeRepository fidelidadeRepository;

    @Autowired
    public ClienteService(ClienteRepository repository, EnderecoService enderecoService, EmailService emailService, ClienteFidelidadeRepository fidelidadeRepository) {
        this.repository = repository;
        this.enderecoService = enderecoService;
        this.emailService = emailService;
        this.fidelidadeRepository = fidelidadeRepository; // Injetando o repositório de fidelidade
    }

    public List<Cliente> listar() {
        List<Cliente> clientes = repository.findAll();
        
        for (Cliente cliente : clientes) {
            Optional<ClienteFidelidade> fidelidadeOpt = fidelidadeRepository.findByClienteId(cliente.getId());
            if (fidelidadeOpt.isPresent()) {
                int nivelFidelidade = fidelidadeOpt.get().getNivelFidelidade();
                enviarEmailNivelFidelidade(cliente, nivelFidelidade);
            } else {
                System.out.println("Nenhum registro de fidelidade encontrado para o cliente " + cliente.getNome());
                enviarEmailNivelFidelidade(cliente, 1); // Enviar e-mail com nível 0 se não encontrado
            }
        }
        
        return clientes;
    }
    
    public Cliente save(Cliente cliente) throws MessagingException {
        String cep = cliente.getEndereco().getCep();
        
        try {
            EnderecoResponseDTO enderecoResponse = enderecoService.buscar(cep);
            
            // Converter EnderecoResponseDTO para Endereco
            Endereco endereco = new Endereco();
            endereco.setCep(enderecoResponse.getCep());
            endereco.setLogradouro(enderecoResponse.getLogradouro());
            endereco.setBairro(enderecoResponse.getBairro());
            endereco.setLocalidade(enderecoResponse.getLocalidade());
            endereco.setUf(enderecoResponse.getUf());

            cliente.setEndereco(endereco);
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado.");
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao buscar o CEP. Verifique se o CEP é válido.");
        }

        // Se o CEP for válido, prossegue com o salvamento do cliente
        Cliente clienteSalvo = repository.save(cliente);

        emailService.enviarEmail(cliente.getEmail(), "Bem-vindo!", "Olá " + cliente.getNome() + ", seu cadastro foi realizado com sucesso!");

        return clienteSalvo;
    }


    public Cliente editar(Long id, Cliente cliente) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        
        // Valida o CEP ao editar o cliente
        String cep = cliente.getEndereco().getCep();
        
        try {
            enderecoService.buscar(cep); // Validando o CEP antes de salvar a edição
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado.");
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao buscar o CEP. Verifique se o CEP é válido.");
        }
        cliente.setId(id);
        return repository.save(cliente);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
    
    private void enviarEmailNivelFidelidade(Cliente cliente, int nivelFidelidade) {
        String para = cliente.getEmail();
        String assunto = "Informação sobre seu Nível de Fidelidade";
        String texto = String.format(
            "Olá %s,\n\nSeu nível de fidelidade é %d.\n\nObrigado por ser nosso cliente!",
            cliente.getNome(),
            nivelFidelidade
        );

        emailService.enviarEmail(para, assunto, texto);
    }
}
