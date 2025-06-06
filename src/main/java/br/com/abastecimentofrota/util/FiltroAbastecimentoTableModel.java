package br.com.abastecimentofrota.util;

import br.com.abastecimentofrota.DTO.LinhaFiltroAbastecimentoDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.service.AbastecimentoService;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administração01
 */
public class FiltroAbastecimentoTableModel extends AbstractTableModel {

    private final String[] colunas = {
        "Data", "Frota", "Quantidade (L)", "Total R$", "Nota Fiscal", "Cupom Fiscal"
    };

    private List<LinhaFiltroAbastecimentoDTO> linhas = new ArrayList<>();
    private final AbastecimentoService abastecimentoService;

    public FiltroAbastecimentoTableModel(List<LinhaFiltroAbastecimentoDTO> dadosIniciais, AbastecimentoService abastecimentoService) {
        this.linhas = new ArrayList<>(dadosIniciais); // garante lista mutável
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
        LinhaFiltroAbastecimentoDTO linha = linhas.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> linha.getData();
            case 1 -> linha.getFrota();
            case 2 -> linha.getQuantidade();
            case 3 -> linha.getPreco().multiply(linha.getQuantidade()).setScale(2, RoundingMode.HALF_DOWN);
            case 4 -> linha.getNotaFiscal();
            case 5 -> linha.getCupomFiscal();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void atualizarDados(List<LinhaFiltroAbastecimentoDTO> novosDados) {
        this.linhas = new ArrayList<>(novosDados); // evita lista imutável
        fireTableDataChanged();
    }

    public LinhaFiltroAbastecimentoDTO getLinha(int rowIndex) {
        return linhas.get(rowIndex);
    }

    public void removerLinha(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < linhas.size()) {
            linhas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
            this.fireTableDataChanged();
        }
    }
    
}
