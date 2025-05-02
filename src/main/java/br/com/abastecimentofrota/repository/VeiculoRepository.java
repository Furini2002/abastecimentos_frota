/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.model.Veiculo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Administração01
 */
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
    
    Optional<Veiculo> findByFrota(String frota);
    
}
