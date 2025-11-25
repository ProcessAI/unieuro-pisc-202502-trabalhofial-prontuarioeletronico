package view;

import controller.ControllerConvenio;
import model.Convenio;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class TelaConvenio extends javax.swing.JFrame {

    // DECLARAÇÃO DE VARIÁVEIS DE COMPONENTE
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JTextField campoArea;
    private javax.swing.JTextField campoCoparticipacao;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableConvenios; 

    // CONSTRUTOR: Com try-catch para evitar que o erro de conexão trave a tela
    public TelaConvenio() {
        initComponents(); 
        
        try {
            preencherTabela(); 
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, 
                "AVISO: A tela abriu, mas a conexão com o PostgreSQL falhou.\n" +
                "Verifique o servidor e a senha no Conexao.java.", 
                "Erro de Conexão no Arranque", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MÉTODO DE LISTAGEM
    public void preencherTabela() {
        DefaultTableModel modelo = (DefaultTableModel) jTableConvenios.getModel();
        modelo.setRowCount(0); 

        ControllerConvenio controller = new ControllerConvenio();
        List<Convenio> lista = controller.listarConvenios();

        for (Convenio c : lista) {
            modelo.addRow(new Object[]{
                c.getConvenioNome(),
                c.getConvenioTipo(),
                c.getConvenioArea(),
                c.getCoparticipacao()
            });
        }
    }

    // CÓDIGO GERADO PELO NETBEANS (Inicializa os componentes visuais)
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConvenios = new javax.swing.JTable(); 
        campoNome = new javax.swing.JTextField();
        campoTipo = new javax.swing.JTextField();
        campoArea = new javax.swing.JTextField();
        campoCoparticipacao = new javax.swing.JTextField();
        botaoSalvar = new javax.swing.JButton();
        javax.swing.JLabel labelNome = new javax.swing.JLabel("Nome:");
        javax.swing.JLabel labelTipo = new javax.swing.JLabel("Tipo:");
        javax.swing.JLabel labelArea = new javax.swing.JLabel("Área:");
        javax.swing.JLabel labelCopart = new javax.swing.JLabel("Coparticipação:");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Convênio");

        jTableConvenios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Nome", "Tipo", "Área", "Coparticipação" }
        ));
        jScrollPane1.setViewportView(jTableConvenios);

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });

        // Configuração do Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoArea, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelArea))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelCopart)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(campoCoparticipacao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(botaoSalvar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(labelTipo)
                    .addComponent(labelArea)
                    .addComponent(labelCopart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCoparticipacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoSalvar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    // AÇÃO DO BOTÃO SALVAR
    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        String nome = campoNome.getText();
        String tipo = campoTipo.getText();
        String area = campoArea.getText();
        String copart = campoCoparticipacao.getText();

        ControllerConvenio controller = new ControllerConvenio();
        
        try {
             boolean sucesso = controller.salvar(nome, tipo, area, copart);

             if (sucesso) {
                 JOptionPane.showMessageDialog(this, "Convênio Salvo com Sucesso!");
                 preencherTabela();
                 campoNome.setText("");
                 campoTipo.setText("");
                 campoArea.setText("");
                 campoCoparticipacao.setText("");
             } else {
                 JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados.");
             }
        } catch (RuntimeException e) {
             JOptionPane.showMessageDialog(this, "Erro de Conexão: Não foi possível salvar. Verifique o PostgreSQL.", "Erro de BD", JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConvenio().setVisible(true);
            }
        });
    }
}
