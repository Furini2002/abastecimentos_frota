/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.repository;

import br.com.abastecimentofrota.DTO.LinhaFiltroAbastecimentoDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import java.util.List;

/**
 *
 * @author Administração01
 */
public interface AbastecimentoRepositoryCustom {

    List<Abastecimento> buscarComFiltro(LinhaFiltroAbastecimentoDTO filtro);

}
