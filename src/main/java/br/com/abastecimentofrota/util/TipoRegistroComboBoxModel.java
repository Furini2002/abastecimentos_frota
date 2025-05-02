/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.util;

import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Administração01
 */
public class TipoRegistroComboBoxModel extends DefaultComboBoxModel<TipoRegistro>{

    public TipoRegistroComboBoxModel() {
        super();
        // Adiciona todos os valores do enum ao combo
        Arrays.stream(TipoRegistro.values()).forEach(this::addElement);
    }

    @Override
    public TipoRegistro getSelectedItem() {
        return (TipoRegistro) super.getSelectedItem();
    }

}
