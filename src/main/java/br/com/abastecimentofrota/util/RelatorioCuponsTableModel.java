/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abastecimentofrota.util;

import br.com.abastecimentofrota.DTO.LinhaRelatorioCupomDTO;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administração01
 */
public class RelatorioCuponsTableModel extends AbstractTableModel {

    private List<LinhaRelatorioCupomDTO> linhas;
    private final String[] colunas = {
        "Nota Fiscal", "Data NF", "Nº Cupom", "Data Cupom", "Litros", "Valor", "Tipo Combustível"
    };

    public RelatorioCuponsTableModel(List<LinhaRelatorioCupomDTO> linhas) {
        this.linhas = linhas;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LinhaRelatorioCupomDTO linha = linhas.get(rowIndex);

        if (linha.isIsTotal()) {
            return switch (columnIndex) {
                case 0 ->
                    "TOTAL NF " + linha.getNumeroNota();
                case 4 ->
                    linha.getLitros();
                case 5 ->
                    linha.getValor();
                default ->
                    "";
            };
        }

        return switch (columnIndex) {
            case 0 ->
                linha.getNumeroNota();
            case 1 ->
                linha.getDataNota();
            case 2 ->
                linha.getNumeroCupom();
            case 3 ->
                linha.getDataCupom();
            case 4 ->
                linha.getLitros();
            case 5 ->
                linha.getValor() != null ? linha.getValor().multiply(linha.getLitros()).setScale(2, RoundingMode.HALF_UP) : "";
            case 6 ->
                linha.getTipoCombustivel();
            default ->
                null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void setDados(List<LinhaRelatorioCupomDTO> novosDados) {
        this.linhas = novosDados;
        fireTableDataChanged();
    }

    public LinhaRelatorioCupomDTO getLinha(int rowIndex) {
        return linhas.get(rowIndex);
    }
}
