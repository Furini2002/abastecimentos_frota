/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.util;

/**
 *
 * @author Administração01
 */
public enum TipoCombustivel {
    GASOLINA("GASOLINA"),
    DIESEL("DIESEL");
    
    private final String descricao;

    TipoCombustivel(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
