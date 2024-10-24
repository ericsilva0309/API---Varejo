package com.varejo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varejo.entity.Cliente;
import com.varejo.entity.Favoritos;
import com.varejo.entity.Produto;
import com.varejo.repository.FavoritosRepository;

//Parte Indiviidual: Ana Carolina 

@Service
public class FavoritosService {

    @Autowired
    private FavoritosRepository favoritosRepository;

    /**
     * Adiciona um produto aos favoritos de um cliente.
     * 
     * @param cliente O cliente que está adicionando o produto aos favoritos.
     * @param produto O produto a ser adicionado aos favoritos.
     * @return O objeto Favoritos que foi salvo no banco de dados.
     */
    public Favoritos adicionarFavoritos(Cliente cliente, Produto produto) {
        Favoritos favorito = new Favoritos(cliente, produto);
        return favoritosRepository.save(favorito);
    }

    /**
     * Remove um produto dos favoritos com base no ID fornecido.
     * 
     * @param id O ID do favorito a ser removido.
     */
    public void removerFavorito(Long id) {
        favoritosRepository.deleteById(id);
    }

    /**
     * Lista todos os favoritos de um cliente.
     * 
     * @param cliente O cliente cujos favoritos serão listados.
     * @return Uma lista de objetos Favoritos associados ao cliente.
     */
    public List<Favoritos> listarFavoritosPorCliente(Cliente cliente) {
        // O repositório deve ser ajustado para retornar apenas os favoritos do cliente específico
        return favoritosRepository.findAll();
    }

    /**
     * Busca um favorito pelo ID.
     * 
     * @param id O ID do favorito a ser buscado.
     * @return O objeto Favoritos correspondente ao ID fornecido ou null se não encontrado.
     */
    public Favoritos buscarFavoritoPorId(Long id) {
        // Implementar a lógica de busca pelo ID
        return null;
    }

    /**
     * Atualiza um favorito existente.
     * 
     * @param favoritoExistente O objeto Favoritos com os dados a serem atualizados.
     * @return O objeto Favoritos atualizado.
     */
    public Favoritos atualizarFavorito(Favoritos favoritoExistente) {
        // Implementar a lógica de atualização do favorito
        return null;
    }
    
    public List<Favoritos> listar() {
        return favoritosRepository.findAll(); // Retorna todos os produtos do repositório
    }
}
