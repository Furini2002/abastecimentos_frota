/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.util;

/**
 *
 * @author Administração01
 */
public enum TipoRegistro {
    DIARIO("Diário de Bordo"),
    CUPOM("Cupom Fiscal");

    private final String descricao;

    TipoRegistro(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
