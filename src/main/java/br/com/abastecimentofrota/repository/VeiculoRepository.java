/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.model.Veiculo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administração01
 */
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByFrota(String frota);

    @Query("SELECT v FROM Veiculo v WHERE v.placa LIKE %:placa%")
    List<Veiculo> findByPlacaLike(@Param("placa") String placa);

}
