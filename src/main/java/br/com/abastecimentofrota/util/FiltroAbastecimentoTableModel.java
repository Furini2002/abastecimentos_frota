package br.com.abastecimentofrota.util;

import br.com.abastecimentofrota.DTO.LinhaFiltroAbastecimentoDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.service.AbastecimentoService;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administração01
 */
public class FiltroAbastecimentoTableModel extends AbstractTableModel {

    private List<LinhaFiltroAbastecimentoDTO> linhas;
    private final String[] colunas = {
        "Data", "Frota", "Quantidade(L)", "Total R$", "Nota Fiscal", "Cupom Fiscal"
    };
    private final AbastecimentoService abastecimentoService;

    public FiltroAbastecimentoTableModel(List<LinhaFiltroAbastecimentoDTO> linhas, AbastecimentoService abastecimentoService) {
        this.linhas = linhas;
        this.abastecimentoService = abastecimentoService;
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
        LinhaFiltroAbastecimentoDTO linha = linhas.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                linha.getData();
            case 1 ->
                linha.getFrota();
            case 2 ->
                linha.getQuantidade();
            case 3 ->
                linha.getPreco().multiply(linha.getQuantidade()).setScale(2, RoundingMode.HALF_DOWN);
            case 4 ->
                linha.getNotaFiscal();
            case 5 ->
                linha.getCupomFiscal();
            default ->
                null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void setDados(List<LinhaFiltroAbastecimentoDTO> novosDados) {
        this.linhas = novosDados;
        fireTableDataChanged();
    }

    public LinhaFiltroAbastecimentoDTO getLinha(int rowIndex) {
        return linhas.get(rowIndex);
    }  

    public void removerLinha(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < linhas.size()) {
            linhas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void setLinhas(List<LinhaFiltroAbastecimentoDTO> novasLinhas) {
        this.linhas = novasLinhas;
        fireTableDataChanged();
    }
    
}
