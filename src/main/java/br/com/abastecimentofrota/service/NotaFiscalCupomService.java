/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.model.NotaFiscal;
import br.com.abastecimentofrota.model.NotaFiscalCupom;
import br.com.abastecimentofrota.model.Posto;
import br.com.abastecimentofrota.repository.NotaFiscalCupomRepository;
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
public class NotaFiscalCupomService {
    private final NotaFiscalCupomRepository repository;

    public NotaFiscalCupomService(NotaFiscalCupomRepository repository) {
        this.repository = repository;
    }

    public List<NotaFiscalCupom> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public NotaFiscalCupom salvar(NotaFiscalCupom nota) {
        return repository.save(nota);
    }
    
    public NotaFiscalCupom buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrado!"));
    }
    
}
