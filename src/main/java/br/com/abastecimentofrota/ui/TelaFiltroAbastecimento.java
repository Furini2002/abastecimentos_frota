package br.com.abastecimentofrota.ui;

import br.com.abastecimentofrota.DTO.LinhaFiltroAbastecimentoDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.service.AbastecimentoService;
import br.com.abastecimentofrota.service.AtualizarAbastecimentoService;
import br.com.abastecimentofrota.service.NotaFiscalCupomService;
import br.com.abastecimentofrota.service.NotaFiscalService;
import br.com.abastecimentofrota.service.RelatorioAbastecimentoService;
import br.com.abastecimentofrota.service.VeiculoService;
import br.com.abastecimentofrota.util.FiltroAbastecimentoTableModel;
import br.com.abastecimentofrota.util.TipoCombustivel;
import br.com.abastecimentofrota.util.TipoCombustivelComboBoxModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TelaFiltroAbastecimento extends javax.swing.JFrame {

    private final AbastecimentoService abastecimentoService;
    private final RelatorioAbastecimentoService relatorioService;
    private final NotaFiscalCupomService notaCupomService;
    private final VeiculoService veiculoService;
    private final AtualizarAbastecimentoService atualizarAbastecimento;
    private Abastecimento abastecimentoSelecionado;

    //valores combobox Mes
    String[] meses = {
        "01 - Janeiro", "02 - Fevereiro", "03 - Março", "04 - Abril",
        "05 - Maio", "06 - Junho", "07 - Julho", "08 - Agosto",
        "09 - Setembro", "10 - Outubro", "11 - Novembro", "12 - Dezembro"
    };

    public TelaFiltroAbastecimento(AtualizarAbastecimentoService atualizarAbastecimento, AbastecimentoService abastecimentoService, RelatorioAbastecimentoService relatorioService, VeiculoService veiculoService, NotaFiscalService notaService, NotaFiscalCupomService notaCupomService) {
        this.abastecimentoService = abastecimentoService;
        this.relatorioService = relatorioService;
        this.notaCupomService = notaCupomService;
        this.veiculoService = veiculoService;
        this.atualizarAbastecimento = atualizarAbastecimento;
        this.setResizable(false);

        initComponents();

        //configurando os combobox
        TipoCombustivelComboBoxModel comboTipoCombustivel = new TipoCombustivelComboBoxModel();
        comboboxTipoCombustivel.setModel(comboTipoCombustivel);

        //configuurando a table
        List<Abastecimento> dados = abastecimentoService.listarTodos();
        List<LinhaFiltroAbastecimentoDTO> dadosConvertidosDTO = abastecimentoService.converterAbastecimentoParaLinhaFiltroAbastecimentoDTO(dados);
        FiltroAbastecimentoTableModel tablemodel = new FiltroAbastecimentoTableModel(dadosConvertidosDTO, this.abastecimentoService);
        tableAbastecimentos.setModel(tablemodel);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tablemodel);
        tableAbastecimentos.setRowSorter(sorter);

        //adicionando evento de apertar enter e filtrar
        getRootPane().setDefaultButton(buttonFiltrar);

        //selecionando o abastcimetno ao selecionar na tabela
        tableAbastecimentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tableAbastecimentos.getSelectedRow();
                    if (selectedRow != -1) {
                        int modelRow = tableAbastecimentos.convertRowIndexToModel(selectedRow);
                        FiltroAbastecimentoTableModel tableModel = (FiltroAbastecimentoTableModel) tableAbastecimentos.getModel();
                        LinhaFiltroAbastecimentoDTO dtoSelecionado = tableModel.getLinha(modelRow);
                        abastecimentoSelecionado = dtoSelecionado.converterParaAbastecimento(dtoSelecionado, veiculoService, notaService);

                    }
                }
            }
        });

        //ajustando o combobox para o mes vigente
        // Obtém o mês atual (1 a 12)
        int mesAtual = LocalDate.now().getMonthValue();
        comboboxMes.setSelectedIndex(mesAtual - 2); //-2 pois, -1 é o indice do mes vigente, e -1 para o mes anterior que normalmente é o mes do lançamento

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableAbastecimentos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        comboboxMes = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboboxTipoCombustivel = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        textPrecoMaior = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textCupomFiscal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textFrota = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textQuantidadeMenor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textQuantidadeMaior = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        textPrecoMenor = new javax.swing.JTextField();
        buttonFiltrar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        textNotaFiscal = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        buttonEditar = new javax.swing.JButton();
        buttonExcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filtrar abastecimentos");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        tableAbastecimentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableAbastecimentos);

        jLabel1.setText("Mês*");

        comboboxMes.setModel(new javax.swing.DefaultComboBoxModel<>(meses));

        jLabel2.setText("Tipo de Combustível*");

        jLabel3.setText("Frota");

        textPrecoMaior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPrecoMaiorActionPerformed(evt);
            }
        });

        jLabel4.setText("Nota Fiscal");

        jLabel5.setText("Preço >  R$");

        textFrota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFrotaActionPerformed(evt);
            }
        });

        jLabel6.setText("Preço < R$");

        textQuantidadeMenor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textQuantidadeMenorActionPerformed(evt);
            }
        });

        jLabel7.setText("Quantidade (lts) >");

        textQuantidadeMaior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textQuantidadeMaiorActionPerformed(evt);
            }
        });

        jLabel8.setText("Quantidade (lts) <");

        textPrecoMenor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPrecoMenorActionPerformed(evt);
            }
        });

        buttonFiltrar.setBackground(new java.awt.Color(76, 175, 80));
        buttonFiltrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonFiltrar.setForeground(new java.awt.Color(255, 255, 255));
        buttonFiltrar.setText("Filtrar");
        buttonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFiltrarActionPerformed(evt);
            }
        });

        jLabel9.setText("Cupom Fiscal");

        buttonEditar.setBackground(new java.awt.Color(255, 235, 59));
        buttonEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonEditar.setText("Editar");
        buttonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarActionPerformed(evt);
            }
        });

        buttonExcluir.setBackground(new java.awt.Color(244, 67, 54));
        buttonExcluir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonExcluir.setText("Excluir");
        buttonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboboxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboboxTipoCombustivel, 0, 128, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFrota, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonEditar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textCupomFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textQuantidadeMaior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel6)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textPrecoMaior, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textPrecoMenor, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(textQuantidadeMenor))
                                .addGap(18, 18, 18)
                                .addComponent(buttonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboboxMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(comboboxTipoCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(textFrota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textCupomFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(textNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textQuantidadeMenor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(textQuantidadeMaior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(textPrecoMaior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(textPrecoMenor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(buttonFiltrar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEditar)
                    .addComponent(buttonExcluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textFrotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFrotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFrotaActionPerformed

    private void textPrecoMaiorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPrecoMaiorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textPrecoMaiorActionPerformed

    private void textQuantidadeMenorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textQuantidadeMenorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textQuantidadeMenorActionPerformed

    private void textQuantidadeMaiorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textQuantidadeMaiorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textQuantidadeMaiorActionPerformed

    private void textPrecoMenorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPrecoMenorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textPrecoMenorActionPerformed

    private void buttonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFiltrarActionPerformed
        try {
            LocalDate data = LocalDate.of(Year.now().getValue(), (comboboxMes.getSelectedIndex() + 1), 1);
            TipoCombustivel tipoCombustivel = (TipoCombustivel) comboboxTipoCombustivel.getSelectedItem();
            String frota = textFrota.getText().trim().isEmpty() ? null : textFrota.getText();
            String notaFiscal = textNotaFiscal.getText().trim().isEmpty() ? null : textNotaFiscal.getText();
            String cupomFiscal = textCupomFiscal.getText().trim().isEmpty() ? null : textCupomFiscal.getText();

            // Verificação segura com valores opcionais
            BigDecimal quantidadeMenorQue = textQuantidadeMenor.getText().trim().isEmpty() ? null
                    : new BigDecimal(textQuantidadeMenor.getText().trim().replace(",", "."));

            BigDecimal quantidadeMaiorQue = textQuantidadeMaior.getText().trim().isEmpty() ? null
                    : new BigDecimal(textQuantidadeMaior.getText().trim().replace(",", "."));

            BigDecimal precoMenorQue = textPrecoMenor.getText().trim().isEmpty() ? null
                    : new BigDecimal(textPrecoMenor.getText().trim().replace(",", "."));

            BigDecimal precoMaiorQue = textPrecoMaior.getText().trim().isEmpty() ? null
                    : new BigDecimal(textPrecoMaior.getText().trim().replace(",", "."));

            // Monta o DTO com os filtros
            LinhaFiltroAbastecimentoDTO filtro = new LinhaFiltroAbastecimentoDTO(
                    data, frota, notaFiscal, cupomFiscal, tipoCombustivel,
                    precoMaiorQue, precoMenorQue, quantidadeMaiorQue, quantidadeMenorQue
            );

            // Chama o serviço para buscar com o filtro
            List<LinhaFiltroAbastecimentoDTO> resultados = relatorioService.buscarComFiltro(filtro);

            // Atualiza a tabela
            FiltroAbastecimentoTableModel tableModel = (FiltroAbastecimentoTableModel) tableAbastecimentos.getModel();
            tableModel.atualizarDados(resultados); // Atualiza os dados sem trocar o modelo

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao converter número: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_buttonFiltrarActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed

    private void buttonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluirActionPerformed
        int respostaExcluir = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir esse registro ?",
                "Exclusão de abastecimento",
                JOptionPane.YES_NO_OPTION
        );

        if (respostaExcluir == JOptionPane.YES_OPTION) {
            try {
                // Remove o vínculo na tabela intermediária (NotaFiscalCupom)
                notaCupomService.excluirCupomPorAbastecimentoId(abastecimentoSelecionado.getId());

                // exclui o abastecimento
                abastecimentoService.excluir(abastecimentoSelecionado);

                // Atualiza apenas a linha removida
                int linhaSelecionada = tableAbastecimentos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    FiltroAbastecimentoTableModel tablemodel = (FiltroAbastecimentoTableModel) tableAbastecimentos.getModel();
                    tablemodel.removerLinha(linhaSelecionada);
                    //tablemodel.fireTableRowsDeleted(linhaSelecionada, linhaSelecionada);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }
    }//GEN-LAST:event_buttonExcluirActionPerformed

    private void buttonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarActionPerformed
        TelaEditarAbastecimento tela = new TelaEditarAbastecimento(atualizarAbastecimento, abastecimentoService, abastecimentoSelecionado, veiculoService);
        tela.setVisible(true);
    }//GEN-LAST:event_buttonEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEditar;
    private javax.swing.JButton buttonExcluir;
    private javax.swing.JButton buttonFiltrar;
    private javax.swing.JComboBox<String> comboboxMes;
    private javax.swing.JComboBox<TipoCombustivel> comboboxTipoCombustivel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableAbastecimentos;
    private javax.swing.JTextField textCupomFiscal;
    private javax.swing.JTextField textFrota;
    private javax.swing.JTextField textNotaFiscal;
    private javax.swing.JTextField textPrecoMaior;
    private javax.swing.JTextField textPrecoMenor;
    private javax.swing.JTextField textQuantidadeMaior;
    private javax.swing.JTextField textQuantidadeMenor;
    // End of variables declaration//GEN-END:variables
}
