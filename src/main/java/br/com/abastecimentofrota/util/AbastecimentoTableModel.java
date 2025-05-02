/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.util;

import br.com.abastecimentofrota.DTO.LinhaAbastecimentoDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administração01
 */
public class AbastecimentoTableModel extends AbstractTableModel {

    private final String[] colunas = {"Dia", "Posto", "Quantidade", "Preço", "Cupom Fiscal"};

    private final List<LinhaAbastecimentoDTO> dados = new ArrayList<>();

    public void adicionarLinha() {
        dados.add(new LinhaAbastecimentoDTO());
        fireTableRowsInserted(dados.size() - 1, dados.size() - 1);
    }

    public void adicionarLinha(LinhaAbastecimentoDTO linha) {
        dados.add(linha);
        fireTableRowsInserted(dados.size() - 1, dados.size() - 1);
    }

    public void removerLinha(int index) {
        if (index >= 0 && index < dados.size()) {
            dados.remove(index);
            fireTableRowsDeleted(index, index);
        }
    }

    public List<LinhaAbastecimentoDTO> getDados() {
        return dados;
    }

    public void limpar() {
        dados.clear();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LinhaAbastecimentoDTO linha = dados.get(rowIndex);
        return switch (columnIndex) {
            case 0 ->
                linha.getDia();
            case 1 ->
                linha.getPosto();
            case 2 ->
                linha.getQuantidade();
            case 3 ->
                linha.getPreco();
            case 4 ->
                linha.getCupomFiscal();
            default ->
                null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        LinhaAbastecimentoDTO linha = dados.get(rowIndex);
        try {
            switch (columnIndex) {
                case 0 -> {
                    int dia = Integer.parseInt(aValue.toString());
                    linha.setDia(dia);
                }
                case 1 ->
                    linha.setPosto(aValue.toString());
                case 2 ->
                    linha.setQuantidade(new BigDecimal(aValue.toString()));
                case 3 ->
                    linha.setPreco(new BigDecimal(aValue.toString()));
                case 4 ->
                    linha.setCupomFiscal(aValue.toString());                
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (Exception e) {
            System.err.println("Erro ao definir valor da célula: " + e.getMessage());
        }
    }

    public List<LinhaAbastecimentoDTO> getLinhas() {
        return dados;
    }

}
