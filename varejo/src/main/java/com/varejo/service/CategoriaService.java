package com.varejo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.varejo.entity.Categoria;
import com.varejo.repository.CategoriaRepository;
import com.varejo.exception.ResourceNotFoundException;
import com.varejo.exception.InvalidDataException;

@Service
public class CategoriaService {

    // Injeção automática do repositório de Categoria, que será usado para acessar o banco de dados.
    @Autowired
    private CategoriaRepository repository;

    /**
     * Lista todas as categorias cadastradas no sistema.
     * @return Uma lista de todas as categorias.
     * @throws ResourceNotFoundException Se não houver nenhuma categoria cadastrada.
     */
    public List<Categoria> listar() {
        // Recupera todas as categorias do banco de dados usando o método findAll() do repositório.
        List<Categoria> categorias = repository.findAll();
        
        // Verifica se a lista de categorias está vazia, lançando uma exceção se não houver nenhuma categoria.
        if (categorias.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma categoria encontrada.");
        }
        
        // Retorna a lista de categorias.
        return categorias;
    }

    /**
     * Insere uma nova categoria no sistema.
     * @param categoria O objeto Categoria a ser inserido.
     * @return A categoria salva no banco de dados.
     * @throws InvalidDataException Se a categoria for nula ou se o nome estiver vazio.
     */
    public Categoria inserir(Categoria categoria) {
        // Valida se a categoria é nula ou se o nome da categoria está vazio.
        if (categoria == null || categoria.getNome() == null || categoria.getNome().isEmpty()) {
            throw new InvalidDataException("Nome da categoria não pode ser nulo ou vazio.");
        }
        
        // Se estiver tudo correto, salva a nova categoria no banco de dados usando o método save().
        return repository.save(categoria);
    }

    /**
     * Edita o nome de uma categoria existente.
     * @param id O ID da categoria a ser editada.
     * @param novoNome O novo nome da categoria.
     * @return A categoria atualizada com o novo nome.
     * @throws ResourceNotFoundException Se a categoria não for encontrada pelo ID fornecido.
     * @throws InvalidDataException Se o novo nome for nulo ou vazio.
     */
    public Categoria editarNome(Long id, String novoNome) {
        // Procura a categoria pelo ID. Caso não encontre, lança uma exceção.
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + id));

        // Valida se o novo nome é nulo ou vazio.
        if (novoNome == null || novoNome.isEmpty()) {
            throw new InvalidDataException("O novo nome não pode ser nulo ou vazio.");
        }

        // Define o novo nome da categoria.
        categoria.setNome(novoNome);
        
        // Salva a categoria atualizada no banco de dados.
        return repository.save(categoria);
    }

    /**
     * Exclui uma categoria do sistema.
     * @param id O ID da categoria a ser excluída.
     * @throws ResourceNotFoundException Se a categoria não for encontrada pelo ID fornecido.
     */
    public void excluir(Long id) {
        // Verifica se a categoria existe no banco de dados. Caso contrário, lança uma exceção.
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada para o ID: " + id);
        }

        // Exclui a categoria pelo ID fornecido usando o método deleteById().
        repository.deleteById(id);
    }
}
