package com.varejo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Categoria;
import com.varejo.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public List<Categoria> listar(){
		return repository.findAll();
	}
	
	public Categoria inserir(Categoria categoria) {
		return repository.save(categoria);
	}
	
	public Categoria editarNome(Long id, String novoNome) {
        Optional<Categoria> optionalCategoria = repository.findById(id);

        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            categoria.setNome(novoNome);
            return repository.save(categoria);
        } else {
            return null;
        }
    }
	
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
}
