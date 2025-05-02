/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.model.Posto;
import br.com.abastecimentofrota.repository.PostoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administração01
 */
@Service
public class PostoService {
    private final PostoRepository repository;

    public PostoService(PostoRepository repository) {
        this.repository = repository;
    }

    public List<Posto> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public Posto salvar(Posto posto) {
        return repository.save(posto);
    }
    
    public Posto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Posto não encontrado!"));
    }
    
}
