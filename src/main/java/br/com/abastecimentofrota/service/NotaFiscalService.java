/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.model.NotaFiscal;
import br.com.abastecimentofrota.model.Posto;
import br.com.abastecimentofrota.repository.NotaFiscalRepository;
import br.com.abastecimentofrota.repository.PostoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administração01
 */
@Service
public class NotaFiscalService {
    private final NotaFiscalRepository repository;

    public NotaFiscalService(NotaFiscalRepository repository) {
        this.repository = repository;
    }

    public List<NotaFiscal> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public NotaFiscal salvar(NotaFiscal nota) {
        return repository.save(nota);
    }
    
    public NotaFiscal buscarPorId(Long id) {
        return repository.findByIdWithCupons(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrado!"));
    }
    
    public NotaFiscal buscarPorNUmero (String id) {
        return repository.findWithNumber(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrado!"));
    }
    
}
