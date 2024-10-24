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
import com.varejo.enums.TipoCupom;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.repository.ClienteFidelidadeRepository;
import com.varejo.repository.ClienteRepository;

import jakarta.mail.MessagingException;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final EnderecoService enderecoService;
    private final EmailService emailService; // Serviço de e-mail para envio de notificações
    private final ClienteFidelidadeRepository fidelidadeRepository;

    
    /**
     * O @Autowired no construtor informa ao Spring que ele deve injetar automaticamente
     * as dependências quando criar a instância da classe ClienteService. Ou seja, o Spring
     * irá fornecer as instâncias de ClienteRepository, EnderecoService, EmailService e 
     * ClienteFidelidadeRepository, sem que seja necessário criar esses objetos manualmente.
     * 
     * Isso permite que a injeção de dependências seja automática, facilitando o controle do 
     * ciclo de vida dos objetos e melhorando a testabilidade do código.
     * 
     * Necessário caso tivesse mais de um construtor, igual na antiga versão do código.
     */
    //@Autowired
    public ClienteService(ClienteRepository repository, EnderecoService enderecoService, EmailService emailService, ClienteFidelidadeRepository fidelidadeRepository) {
        this.repository = repository;
        this.enderecoService = enderecoService;
        this.emailService = emailService;
        this.fidelidadeRepository = fidelidadeRepository; // Injetando o repositório de fidelidade
    }

    /**
     * Lista todos os clientes, verificando o nível de fidelidade e enviando um e-mail.
     * Se o cliente tiver um registro de fidelidade, envia um e-mail com seu nível.
     * Se não houver, o nível padrão de 1 será enviado no e-mail.
     * 
     * @return Lista de clientes.
     */
    public List<Cliente> listar() {
        List<Cliente> clientes = repository.findAll();
        
        for (Cliente cliente : clientes) {
            Optional<ClienteFidelidade> fidelidadeOpt = fidelidadeRepository.findByClienteId(cliente.getId());
            if (fidelidadeOpt.isPresent()) {
                int nivelFidelidade = fidelidadeOpt.get().getNivelFidelidade();
                enviarEmailNivelFidelidade(cliente, nivelFidelidade);
            } else {
                System.out.println("Nenhum registro de fidelidade encontrado para o cliente " + cliente.getNome());
                enviarEmailNivelFidelidade(cliente, 1); // Enviar e-mail com nível 1 se não encontrado
            }
        }
        
        return clientes;
    }

    /**
     * Salva um novo cliente no sistema, realizando uma verificação do CEP antes.
     * Busca o endereço a partir do CEP informado e envia um e-mail de boas-vindas ao cliente.
     * 
     * @param cliente Cliente a ser salvo.
     * @return Cliente salvo com o endereço validado e e-mail enviado.
     * @throws MessagingException Caso haja um erro no envio de e-mail.
     */
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

        emailService.enviarEmailComImagem(cliente.getEmail(), 
                "Bem-vindo!", 
                "Olá " + cliente.getNome() + ", seu cadastro foi realizado com sucesso!<br>" +
                "Para comemorar seu cadastro, disponibilizamos um cupom de 15% de desconto na sua primeira compra. :D<br><br>" +
                "Cupom Primeira Compra: <br>" + TipoCupom.PRIMEIRACOMPRA.getNomePorExtenso(),
                "https://img.myloview.com.br/quadros/oferta-especial-de-15-de-desconto-no-rotulo-ou-no-preco-400-123635673.jpg");

        return clienteSalvo;
    }

    /**
     * Edita os dados de um cliente existente, validando o CEP informado.
     * 
     * @param id ID do cliente a ser editado.
     * @param cliente Cliente com os novos dados.
     * @return Cliente editado e salvo no sistema.
     * @throws ResourceNotFoundException Caso o cliente com o ID especificado não exista.
     */
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
        cliente.setId(id); // Define o ID para garantir que será feita uma edição e não criação de novo registro
        return repository.save(cliente);
    }

    /**
     * Exclui um cliente com base no ID informado.
     * 
     * @param id ID do cliente a ser excluído.
     * @throws ResourceNotFoundException Caso o cliente com o ID especificado não exista.
     */
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
    
    /**
     * Envia um e-mail ao cliente informando seu nível de fidelidade.
     * 
     * @param cliente Cliente a quem o e-mail será enviado.
     * @param nivelFidelidade Nível de fidelidade a ser informado no e-mail.
     */
    public void enviarEmailNivelFidelidade(Cliente cliente, int nivelFidelidade) {
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
