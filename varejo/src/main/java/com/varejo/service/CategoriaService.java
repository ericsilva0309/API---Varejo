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

    @Autowired
    private CategoriaRepository repository;

    // Listar todas as categorias
    public List<Categoria> listar() {
        List<Categoria> categorias = repository.findAll();
        if (categorias.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma categoria encontrada.");
        }
        return categorias;
    }

    // Inserir uma nova categoria
    public Categoria inserir(Categoria categoria) {
        if (categoria == null || categoria.getNome() == null || categoria.getNome().isEmpty()) {
            throw new InvalidDataException("Nome da categoria não pode ser nulo ou vazio.");
        }
        return repository.save(categoria);
    }

    // Editar o nome de uma categoria
    public Categoria editarNome(Long id, String novoNome) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + id));

        if (novoNome == null || novoNome.isEmpty()) {
            throw new InvalidDataException("O novo nome não pode ser nulo ou vazio.");
        }

        categoria.setNome(novoNome);
        return repository.save(categoria);
    }

    // Excluir uma categoria
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada para o ID: " + id);
        }
        repository.deleteById(id);
    }
}
