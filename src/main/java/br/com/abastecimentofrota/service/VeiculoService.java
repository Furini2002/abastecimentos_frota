/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.service;

import br.com.abastecimentofrota.model.Veiculo;
import br.com.abastecimentofrota.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administração01
 */
@Service
public class VeiculoService {

    private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public List<Veiculo> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        return repository.save(veiculo);
    }

    public Veiculo buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veiculo não encontrado!"));
    }

    public Veiculo buscarPorFrota(String frota) {
        return repository.findByFrota(frota).orElse(null);
    }
    
    public List<Veiculo> buscarPorPlaca(String placa){
        return repository.findByPlacaLike(placa);
    }

}
