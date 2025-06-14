/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.abastecimentofrota.ui;

import br.com.abastecimentofrota.model.Veiculo;
import br.com.abastecimentofrota.service.VeiculoService;
import br.com.abastecimentofrota.util.BuscarFrotaTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Administração01
 */
public class TelaBuscarFrota extends javax.swing.JFrame {

    /**
     * Creates new form TelaBuscarFrota
     */
    final VeiculoService veiculoService;
    final TelaCadastroAbastecimento telaAbastecimento;

    public TelaBuscarFrota(VeiculoService veiculoService, TelaCadastroAbastecimento tela) {
        initComponents();
        this.veiculoService = veiculoService;
        this.setResizable(false);
        this.telaAbastecimento = tela;
        
        //setando atalho dos botoes para aparecer
        buttonBuscar.setToolTipText("Enter");

        BuscarFrotaTableModel tableModel = new BuscarFrotaTableModel(veiculoService.listarTodos());
        tableVeiculo.setModel(tableModel);

        //buscar ao apertar a tecla ENTER
        textPlaca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarVeiculoPorPlaca();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        textPlaca = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVeiculo = new javax.swing.JTable();
        buttonBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar veículo por placa");

        jLabel1.setText("Placa");

        tableVeiculo.setModel(new javax.swing.table.DefaultTableModel(
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
        tableVeiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableVeiculoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableVeiculo);

        buttonBuscar.setBackground(new java.awt.Color(76, 175, 80));
        buttonBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonBuscar.setForeground(new java.awt.Color(255, 255, 255));
        buttonBuscar.setText("Buscar");
        buttonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
        buscarVeiculoPorPlaca();
    }//GEN-LAST:event_buttonBuscarActionPerformed

    public void buscarVeiculoPorPlaca() {
        String placa = textPlaca.getText();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Insira uma placa", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        BuscarFrotaTableModel tableModel = (BuscarFrotaTableModel) tableVeiculo.getModel();
        tableModel.setDados(veiculoService.buscarPorPlaca(placa));
    }

    private void tableVeiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableVeiculoMouseClicked
        if (evt.getClickCount() == 2) {
            int linhaSelecionada = tableVeiculo.rowAtPoint(evt.getPoint());

            if (linhaSelecionada != -1) {
                BuscarFrotaTableModel tablemodel = (BuscarFrotaTableModel) tableVeiculo.getModel();
                Veiculo veiculoSelecionado = tablemodel.getLinha(linhaSelecionada);

                telaAbastecimento.setVeiculo(veiculoSelecionado);
                this.dispose();
            }
        }
    }//GEN-LAST:event_tableVeiculoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableVeiculo;
    private javax.swing.JTextField textPlaca;
    // End of variables declaration//GEN-END:variables
}
