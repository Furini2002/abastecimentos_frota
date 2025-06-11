package br.com.abastecimentofrota.ui;

import br.com.abastecimentofrota.model.Abastecimento;
import br.com.abastecimentofrota.model.NotaFiscal;
import br.com.abastecimentofrota.model.Veiculo;
import br.com.abastecimentofrota.service.AbastecimentoService;
import br.com.abastecimentofrota.service.AtualizarAbastecimentoService;
import br.com.abastecimentofrota.service.VeiculoService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 *
 * @author Administração01
 */
public class TelaEditarAbastecimento extends javax.swing.JFrame {

    private final AbastecimentoService abastecimentoService;
    private final VeiculoService veiculoService;
    private Abastecimento abastecimentoOriginal;
    private boolean dadosForamAlterados = false;
    private final AtualizarAbastecimentoService atualizarAbastecimento;
    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaEditarAbastecimento(AtualizarAbastecimentoService atualizarAbastecimento, AbastecimentoService abastecimentoService, Abastecimento abastecimentoParaEditar, VeiculoService veiculoService) {
        initComponents();
        this.abastecimentoService = abastecimentoService;
        this.abastecimentoOriginal = abastecimentoParaEditar;
        this.veiculoService = veiculoService;
        this.atualizarAbastecimento = atualizarAbastecimento;

        this.setResizable(false);

        carregarDados(abastecimentoParaEditar);
        configurarListeners();

    }

    public void carregarDados(Abastecimento abastecimentoParaEditar) {
        System.out.println(abastecimentoParaEditar.getCupomFiscal());
        System.out.println(abastecimentoParaEditar.getVeiculo().getFrota());

        //setando os valores nos text fields
        tfNotaFiscal.setText(this.abastecimentoOriginal.getNotaFiscal() != null ? this.abastecimentoOriginal.getNotaFiscal().getNumero() : "");
        tfCupomFiscal.setText(this.abastecimentoOriginal.getCupomFiscal());
        tfFrota.setText(this.abastecimentoOriginal.getVeiculo().getFrota());
        tfQuantidade.setText(this.abastecimentoOriginal.getQuantidade().toString());
        //formatando a data para colocar no textfield        
        String dataFormatada = this.abastecimentoOriginal.getData().format(formatador);
        tfData.setText(dataFormatada);

        buttonEditar.setEnabled(false);
    }

    private void configurarListeners() {
        javax.swing.event.DocumentListener listenerDeAlteracao = new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                habilitarBotaoEdicao();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                habilitarBotaoEdicao();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                habilitarBotaoEdicao();
            }
        };

        // Adiciona o mesmo listener a todos os campos
        tfNotaFiscal.getDocument().addDocumentListener(listenerDeAlteracao);
        tfData.getDocument().addDocumentListener(listenerDeAlteracao);
        tfQuantidade.getDocument().addDocumentListener(listenerDeAlteracao);
        tfCupomFiscal.getDocument().addDocumentListener(listenerDeAlteracao);
        tfFrota.getDocument().addDocumentListener(listenerDeAlteracao);
    }

    private void habilitarBotaoEdicao() {
        // Uma vez que qualquer campo foi alterado, habilita o botão
        if (!buttonEditar.isEnabled()) {
            buttonEditar.setEnabled(true);
            dadosForamAlterados = true;
        }
    }

    private void salvarAlteracoes() {
        if (!dadosForamAlterados) {
            JOptionPane.showMessageDialog(this, "Nenhuma alteração foi feita.");
            return;
        }

        StringBuilder mudancas = new StringBuilder("Campos alterados:\n");

        // === NOTA FISCAL ===
        String notaFiscalDoTextField = tfNotaFiscal.getText().isEmpty() ? null : tfNotaFiscal.getText();
        NotaFiscal notaFiscal = abastecimentoOriginal.getNotaFiscal();
        String numeroNotaFiscal = (notaFiscal != null) ? notaFiscal.getNumero() : null;

        if (!Objects.equals(notaFiscalDoTextField, numeroNotaFiscal)) {
            mudancas.append("- Nota Fiscal\n");
            if (notaFiscal == null) {
                notaFiscal = new NotaFiscal();
            }
            notaFiscal.setNumero(notaFiscalDoTextField);
            abastecimentoOriginal.setNotaFiscal(notaFiscal);
        }

        // === QUANTIDADE ===
        try {
            BigDecimal quantidadeDigitada = new BigDecimal(tfQuantidade.getText().replace(",", "."));
            if (abastecimentoOriginal.getQuantidade() == null
                    || abastecimentoOriginal.getQuantidade().compareTo(quantidadeDigitada) != 0) {
                mudancas.append("- Quantidade\n");
                abastecimentoOriginal.setQuantidade(quantidadeDigitada);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // === CUPOM FISCAL ===
        String cupomFiscalDoTextField = tfCupomFiscal.getText().isEmpty() ? null : tfCupomFiscal.getText();
        if (!Objects.equals(cupomFiscalDoTextField, abastecimentoOriginal.getCupomFiscal())) {
            mudancas.append("- Cupom Fiscal\n");
            abastecimentoOriginal.setCupomFiscal(cupomFiscalDoTextField);
        }

        // === DATA ===
        try {
            LocalDate dataDoTextField = LocalDate.parse(tfData.getText(), formatador);
            if (!Objects.equals(dataDoTextField, abastecimentoOriginal.getData())) {
                mudancas.append("- Data\n");
                abastecimentoOriginal.setData(dataDoTextField);
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida.", "Erro de data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // === FROTA ===
        String frotaDigitada = tfFrota.getText();
        Veiculo veiculoAtual = abastecimentoOriginal.getVeiculo();
        String frotaAtual = (veiculoAtual != null) ? veiculoAtual.getFrota() : null;

        if (!Objects.equals(frotaDigitada, frotaAtual)) {
            mudancas.append("- Frota\n");
            try {
                Veiculo novoVeiculo = veiculoService.buscarPorFrota(frotaDigitada);
                abastecimentoOriginal.setVeiculo(novoVeiculo);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Veículo não encontrado", "Erro ao buscar veículo", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // === LOG de alterações ===
        System.out.println(mudancas.toString());
        System.out.println("nota fiscal: " + ((abastecimentoOriginal.getNotaFiscal() != null) ? abastecimentoOriginal.getNotaFiscal().getNumero() : null)
                + "; quantidade: " + abastecimentoOriginal.getQuantidade()
                + "; cupom fiscal: " + abastecimentoOriginal.getCupomFiscal()
                + "; data: " + abastecimentoOriginal.getData()
                + "; frota: " + ((abastecimentoOriginal.getVeiculo() != null) ? abastecimentoOriginal.getVeiculo().getFrota() : null));

        atualizarAbastecimento.atualizarAbastecimento(abastecimentoOriginal);
        JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfData = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfFrota = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfQuantidade = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfNotaFiscal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfCupomFiscal = new javax.swing.JTextField();
        buttonEditar = new javax.swing.JButton();
        buttonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar abastecimento");

        jLabel1.setText("Data");

        jLabel2.setText("Frota");

        jLabel3.setText("Quantidade");

        jLabel4.setText("Nota Fiscal");

        jLabel5.setText("Cupom Fiscal");

        buttonEditar.setBackground(new java.awt.Color(255, 235, 59));
        buttonEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonEditar.setText("Editar");
        buttonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarActionPerformed(evt);
            }
        });

        buttonCancelar.setBackground(new java.awt.Color(244, 67, 54));
        buttonCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonEditar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfCupomFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 2, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfFrota)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tfCupomFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(tfFrota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEditar)
                    .addComponent(buttonCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_buttonCancelarActionPerformed

    private void buttonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarActionPerformed
        salvarAlteracoes();
    }//GEN-LAST:event_buttonEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancelar;
    private javax.swing.JButton buttonEditar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField tfCupomFiscal;
    private javax.swing.JTextField tfData;
    private javax.swing.JTextField tfFrota;
    private javax.swing.JTextField tfNotaFiscal;
    private javax.swing.JTextField tfQuantidade;
    // End of variables declaration//GEN-END:variables
}
