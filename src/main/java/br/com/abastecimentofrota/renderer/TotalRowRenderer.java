/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.renderer;

import br.com.abastecimentofrota.DTO.LinhaRelatorioCupomDTO;
import br.com.abastecimentofrota.util.RelatorioCuponsTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Administração01
 */
public class TotalRowRenderer extends DefaultTableCellRenderer{
    
     @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        RelatorioCuponsTableModel model = (RelatorioCuponsTableModel) table.getModel();
        LinhaRelatorioCupomDTO linha = model.getLinha(row);

        if (linha.isIsTotal()) {
            c.setFont(c.getFont().deriveFont(Font.BOLD));

            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else {
                c.setBackground(new Color(230, 230, 250)); // lilás claro
                c.setForeground(Color.BLACK);
            }
        } else {
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
        }

        return c;
    }
}
    

