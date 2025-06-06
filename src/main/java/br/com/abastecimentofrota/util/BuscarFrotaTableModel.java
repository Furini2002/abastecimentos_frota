package br.com.abastecimentofrota.util;

import br.com.abastecimentofrota.model.Veiculo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administração01
 */
public class BuscarFrotaTableModel extends AbstractTableModel {

    private List<Veiculo> linhas;
    private final String[] colunas = {
        "Frota", "Placa", "Modelo", "Combustível"
    };
    

    public BuscarFrotaTableModel(List<Veiculo> linhas) {
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
        if (rowIndex < 0 || rowIndex >= linhas.size()) {
            return null;
        }
        Veiculo linha = linhas.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                linha.getFrota();
            case 1 ->
                linha.getPlaca();
            case 2 ->
                linha.getModelo();
            case 3 ->
                linha.getTipoCombustivel();          
            default ->
                null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void setDados(List<Veiculo> novosDados) {
        this.linhas = novosDados;
        fireTableDataChanged();
    }

    public Veiculo getLinha(int rowIndex) {
        return linhas.get(rowIndex);
    }  

    public void removerLinha(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < linhas.size()) {
            linhas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void setLinhas(List<Veiculo> novasLinhas) {
        this.linhas = novasLinhas;
        fireTableDataChanged();
    }
    
    
    
}
