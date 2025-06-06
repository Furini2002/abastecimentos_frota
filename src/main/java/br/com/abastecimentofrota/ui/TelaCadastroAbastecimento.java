/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.abastecimentofrota.ui;

import br.com.abastecimentofrota.DTO.LinhaAbastecimentoDTO;
import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.model.Posto;
import br.com.abastecimentofrota.model.Veiculo;
import br.com.abastecimentofrota.service.AbastecimentoService;
import br.com.abastecimentofrota.service.PostoService;
import br.com.abastecimentofrota.service.VeiculoService;
import br.com.abastecimentofrota.util.AbastecimentoTableModel;
import br.com.abastecimentofrota.util.TipoRegistro;
import br.com.abastecimentofrota.util.TipoRegistroComboBoxModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Administração01
 */
public class TelaCadastroAbastecimento extends javax.swing.JFrame {

    /**
     * Creates new form AbastecimentoCadastrar
     */
    private final VeiculoService veiculoService;
    private final AbastecimentoService abastecimentoService;
    private final PostoService postoService;

    Veiculo veiculo;
    AbastecimentoTableModel tablemodel = new AbastecimentoTableModel();
    String[] meses = {
        "01 - Janeiro", "02 - Fevereiro", "03 - Março", "04 - Abril",
        "05 - Maio", "06 - Junho", "07 - Julho", "08 - Agosto",
        "09 - Setembro", "10 - Outubro", "11 - Novembro", "12 - Dezembro"
    };

    public TelaCadastroAbastecimento(VeiculoService veiculoService, AbastecimentoService abastecimentoService, PostoService postoService) {
        this.veiculoService = veiculoService;
        this.abastecimentoService = abastecimentoService;
        this.postoService = postoService;
        initComponents();
        this.setResizable(false);
        
        //setando o atalho dos botões para aparecerem
        btnBuscarPorPlaca.setToolTipText("CTRL + B");
        btnBuscar.setToolTipText("Enter");
        btnSalvar.setToolTipText("CTRL + S");

        //atalho para salvar ctrl+s
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "salvar");

        getRootPane().getActionMap().put("salvar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableAbastecimento.isEditing()) {
                    tableAbastecimento.getCellEditor().stopCellEditing();
                }
                salvarTodosAbastecimentos();
            }
        });

        //configurando a table        
        tableAbastecimento.setModel(tablemodel);

        //centralizando as informaões da tabela
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tableAbastecimento.getColumnCount(); i++) {
            tableAbastecimento.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTextField quantidadeField = new JTextField();
        DefaultCellEditor quantidadeEditor = new DefaultCellEditor(quantidadeField) {
            @Override
            public boolean stopCellEditing() {
                String texto = quantidadeField.getText().replace(",", ".");
                try {
                    new BigDecimal(texto); // Validação
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Quantidade inválida.");
                    return false;
                }
                return super.stopCellEditing();
            }

            @Override
            public Object getCellEditorValue() {
                String texto = quantidadeField.getText().replace(",", ".");
                return new BigDecimal(texto);
            }
        };

        tableAbastecimento.getColumnModel().getColumn(2).setCellEditor(quantidadeEditor);

        //configurando o combobox
        TipoRegistroComboBoxModel comboTipoRegistro = new TipoRegistroComboBoxModel();
        comboboxTipoRegistro.setModel(comboTipoRegistro);
        comboboxTipoRegistro.setSelectedIndex(1);

        //text fields não editaveis
        tfPlaca.setEditable(false);
        tfPlaca.setBackground(new Color(220, 220, 220));
        tfCombustível.setEditable(false);
        tfCombustível.setBackground(new Color(220, 220, 220));

        //ajustando o combobox para o mes vigente
        // Obtém o mês atual (1 a 12)
        int mesAtual = LocalDate.now().getMonthValue();
        comboboxMeses.setSelectedIndex(mesAtual - 2); //-2 pois, -1 é o indice do mes vigente, e -1 para o mes anterior que normalmente é o mes do lançamento

        //buscar ao apertar enter
        tfFrota.addActionListener(e -> BuscarVeiculoPorFrota());

        //adicionar uma nova linha abaixo
        tableAbastecimento.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                int row = tableAbastecimento.getSelectedRow();
                int col = tableAbastecimento.getSelectedColumn();
                int lastCol = tableAbastecimento.getColumnCount() - 1;

                if (col == lastCol && row == tablemodel.getRowCount() - 1) {
                    adicionarLinhaComPosto(veiculo);
                }
            }
        });
    }

    private void salvarTodosAbastecimentos() {
        try {
            TipoRegistro tipoRegistro = (TipoRegistro) comboboxTipoRegistro.getSelectedItem();
            int mesSelecionado = comboboxMeses.getSelectedIndex() + 1; // Janeiro = 0
            int anoAtual = LocalDate.now().getYear(); // ou outro ano selecionável

            List<LinhaAbastecimentoDTO> linhas = tablemodel.getLinhas();
            for (LinhaAbastecimentoDTO linha : linhas) {
                if (linha.getDia() == null || linha.getQuantidade() == null) {
                    continue;
                }

                Abastecimento abastecimento = new Abastecimento();

                // Setando veículo
                abastecimento.setVeiculo(veiculo);

                // Montando a data
                LocalDate data = LocalDate.of(anoAtual, mesSelecionado, linha.getDia());
                abastecimento.setData(data);

                // Buscando o posto
                Long codigoPosto = Long.valueOf(linha.getPosto());
                Posto posto = postoService.buscarPorId(codigoPosto);
                if (posto == null) {
                    JOptionPane.showMessageDialog(this, "Posto de código " + posto + " não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                abastecimento.setPosto(posto);
                abastecimento.setQuantidade(linha.getQuantidade());
                abastecimento.setCupomFiscal(linha.getCupomFiscal());
                abastecimento.setTipoRegistro(tipoRegistro);
                abastecimento.setPreco(linha.getPreco());

                // Salvando abastecimento
                abastecimentoService.salvar(abastecimento);
            }

            JOptionPane.showMessageDialog(this, "Abastecimentos salvos com sucesso!");
            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar abastecimentos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelFrota = new javax.swing.JLabel();
        tfFrota = new javax.swing.JTextField();
        labelPlaca = new javax.swing.JLabel();
        tfPlaca = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        comboboxTipoRegistro = new javax.swing.JComboBox<>();
        labelPlaca1 = new javax.swing.JLabel();
        tfCombustível = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAbastecimento = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        comboboxMeses = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        btnBuscarPorPlaca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Abastecimentos");

        labelFrota.setText("Número da Frota*");

        tfFrota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFrotaActionPerformed(evt);
            }
        });

        labelPlaca.setText("Placa do Veículo");

        tfPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPlacaActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo de cadastro:");

        btnSalvar.setBackground(new java.awt.Color(76, 175, 80));
        btnSalvar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("Salvar Abastecimentos");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        labelPlaca1.setText("Tipo de Combustível");

        tfCombustível.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCombustívelActionPerformed(evt);
            }
        });

        tableAbastecimento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(tableAbastecimento);

        btnBuscar.setBackground(new java.awt.Color(76, 175, 80));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel1.setText("Mês:");

        comboboxMeses.setModel(new javax.swing.DefaultComboBoxModel<>(meses));

        jLabel2.setText("Lista de abastecimentos");

        btnBuscarPorPlaca.setBackground(new java.awt.Color(76, 127, 207));
        btnBuscarPorPlaca.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarPorPlaca.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPorPlaca.setText("Encontrar veículo");
        btnBuscarPorPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPorPlacaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboboxTipoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboboxMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelPlaca)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelPlaca1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelFrota)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfFrota, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBuscarPorPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfCombustível))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFrota)
                    .addComponent(tfFrota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnBuscarPorPlaca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPlaca)
                    .addComponent(tfPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPlaca1)
                    .addComponent(tfCombustível, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboboxTipoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(comboboxMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPlacaActionPerformed

    private void tfCombustívelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCombustívelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCombustívelActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        BuscarVeiculoPorFrota();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarTodosAbastecimentos();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void tfFrotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFrotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFrotaActionPerformed

    private void btnBuscarPorPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorPlacaActionPerformed
        BuscarFrota buscarFrota = new BuscarFrota(veiculoService, this);
        buscarFrota.setVisible(true);
    }//GEN-LAST:event_btnBuscarPorPlacaActionPerformed

    public void BuscarVeiculoPorFrota() {
        String frota = tfFrota.getText().trim();

        if (frota.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite uma placa para buscar.");
            return;
        }

        veiculo = veiculoService.buscarPorFrota(frota);

        if (veiculo != null) {
            tfPlaca.setText(veiculo.getPlaca());
            tfCombustível.setText(veiculo.getTipoCombustivel().toString());

            adicionarLinhaComPosto(veiculo);

            // Foco na nova linha            
            AbastecimentoTableModel model = (AbastecimentoTableModel) tableAbastecimento.getModel();
            int row = model.getRowCount() - 1;
            tableAbastecimento.scrollRectToVisible(tableAbastecimento.getCellRect(row, 0, true));
            tableAbastecimento.changeSelection(row, 0, false, false);
            tableAbastecimento.editCellAt(row, 0);
            tableAbastecimento.getEditorComponent().requestFocus();

        } else {
            JOptionPane.showMessageDialog(this, "Veículo não encontrado.");
        }
    }

    public void adicionarLinhaComPosto(Veiculo veiculo) {
        // Determina o código do posto com base no tipo de combustível
        Posto posto = new Posto();
        String tipo = veiculo.getTipoCombustivel().toString().toLowerCase();

        if (tipo.contains("diesel")) {
            posto = postoService.buscarPorId(3L);
        } else if (tipo.contains("gasolina")) {
            posto = postoService.buscarPorId(2L);
        }

        // Cria nova linha com o código do posto preenchido automaticamente
        AbastecimentoTableModel model = (AbastecimentoTableModel) tableAbastecimento.getModel();
        LinhaAbastecimentoDTO novaLinha = new LinhaAbastecimentoDTO();
        novaLinha.setPosto(posto.getId().toString());
        novaLinha.setPreco(posto.getPreco());

        model.adicionarLinha(novaLinha);
    }

    public void limparCampos() {
        tablemodel.getLinhas().clear();
        tablemodel.fireTableDataChanged();
        tfFrota.setText("");
        tfPlaca.setText("");
        tfCombustível.setText("");
        tfFrota.requestFocusInWindow();
        //tablemodel.adicionarLinha();
    }
    
    public void setVeiculo(Veiculo veiculo){
        tfFrota.setText(veiculo.getFrota());
        BuscarVeiculoPorFrota();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarPorPlaca;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> comboboxMeses;
    private javax.swing.JComboBox<TipoRegistro> comboboxTipoRegistro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelFrota;
    private javax.swing.JLabel labelPlaca;
    private javax.swing.JLabel labelPlaca1;
    private javax.swing.JTable tableAbastecimento;
    private javax.swing.JTextField tfCombustível;
    private javax.swing.JTextField tfFrota;
    private javax.swing.JTextField tfPlaca;
    // End of variables declaration//GEN-END:variables
}
