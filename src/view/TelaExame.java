package view;

import controller.ControllerExame;
import model.Exame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaExame extends JFrame {

    private JTextField tId, tNome, tTipo, tStatus, tHoraI, tHoraF;
    private JTextArea tOrientacao;

    private JTable tabela;
    private JButton bDeletar;
    private JButton bLimpar;

    ControllerExame controller = new ControllerExame();

    public TelaExame() {
        setTitle("Cadastro de Exames");
        setSize(700, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("ID:"); l1.setBounds(20, 20, 80, 25); add(l1);
        tId = new JTextField(); tId.setBounds(100, 20, 100, 25); add(tId);

        JLabel l2 = new JLabel("Nome:"); l2.setBounds(20, 60, 80, 25); add(l2);
        tNome = new JTextField(); tNome.setBounds(100, 60, 200, 25); add(tNome);

        JLabel l3 = new JLabel("Tipo:"); l3.setBounds(20, 100, 80, 25); add(l3);
        tTipo = new JTextField(); tTipo.setBounds(100, 100, 50, 25); add(tTipo);

        JLabel l4 = new JLabel("Status:"); l4.setBounds(20, 140, 80, 25); add(l4);
        tStatus = new JTextField(); tStatus.setBounds(100, 140, 50, 25); add(tStatus);

        JLabel l5 = new JLabel("Orientação:"); l5.setBounds(20, 180, 100, 25); add(l5);
        tOrientacao = new JTextArea(); tOrientacao.setBounds(100, 180, 250, 80); add(tOrientacao);

        JLabel l6 = new JLabel("Hora início:"); l6.setBounds(370, 20, 80, 25); add(l6);
        tHoraI = new JTextField(); tHoraI.setBounds(450, 20, 80, 25); add(tHoraI);

        JLabel l7 = new JLabel("Hora fim:"); l7.setBounds(370, 60, 80, 25); add(l7);
        tHoraF = new JTextField(); tHoraF.setBounds(450, 60, 80, 25); add(tHoraF);

        JButton bSalvar = new JButton("Salvar");
        bSalvar.setBounds(370, 140, 100, 30);
        add(bSalvar);
        bSalvar.addActionListener(e -> salvarOuAtualizar());

        bDeletar = new JButton("Deletar");
        bDeletar.setBounds(480, 140, 100, 30);
        add(bDeletar);
        bDeletar.addActionListener(e -> deletarRegistro());

        bLimpar = new JButton("Limpar");
        bLimpar.setBounds(590, 140, 80, 30);
        add(bLimpar);
        bLimpar.addActionListener(e -> limparCampos());

        tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 280, 640, 150);
        add(scroll);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                preencherCamposComSelecao();
            }
        });

        carregarTabela();

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void salvarOuAtualizar() {
        Exame ex = new Exame();
        String idText = tId.getText().trim();

        try {
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O campo ID é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ex.setExame_id(Integer.parseInt(idText));
            ex.setExame_nome(tNome.getText());
            ex.setExame_tipo(tTipo.getText());
            ex.setExame_status(tStatus.getText());
            ex.setExame_orientacao(tOrientacao.getText());
            ex.setExame_horai(tHoraI.getText());
            ex.setExame_horaf(tHoraF.getText());

            if (tId.isEditable()) {
                controller.cadastrar(ex);
                JOptionPane.showMessageDialog(this, "Exame cadastrado!");
            } else {
                controller.atualizar(ex);
                JOptionPane.showMessageDialog(this, "Exame atualizado!");
            }

            carregarTabela();
            limparCampos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID e campos numéricos devem ser válidos. " + nfe.getMessage(), "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception excep) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar/atualizar. Verifique o formato do Status (CHAR/VARCHAR) e Horas (HH:MM:SS): " + excep.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCamposComSelecao() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            // Preenche os campos de texto com os valores da linha selecionada
            tId.setText(tabela.getValueAt(linha, 0).toString());
            tNome.setText(tabela.getValueAt(linha, 1).toString());
            tTipo.setText(tabela.getValueAt(linha, 2).toString());
            tStatus.setText(tabela.getValueAt(linha, 3).toString());
            tHoraI.setText(tabela.getValueAt(linha, 4).toString());
            tHoraF.setText(tabela.getValueAt(linha, 5).toString());

            // DESABILITA o campo ID (Pronto para atualização)
            tId.setEditable(false);
        }
    }

    private void deletarRegistro() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro na tabela para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idString = tabela.getValueAt(linhaSelecionada, 0).toString();
        int idParaDeletar = Integer.parseInt(idString);

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o Exame ID: " + idParaDeletar + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                controller.deletar(idParaDeletar);
                carregarTabela();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Registro ID " + idParaDeletar + " excluído com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir registro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        tId.setText("");
        tNome.setText("");
        tTipo.setText("");
        tStatus.setText("");
        tOrientacao.setText("");
        tHoraI.setText("");
        tHoraF.setText("");

        tId.setEditable(true);
        tabela.clearSelection();
    }

    private void carregarTabela() {
        List<Exame> lista = controller.listar();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Tipo");
        model.addColumn("Status");
        model.addColumn("Início");
        model.addColumn("Fim");

        for (Exame e : lista) {
            model.addRow(new Object[]{
                    e.getExame_id(),
                    e.getExame_nome(),
                    e.getExame_tipo(),
                    e.getExame_status(),
                    e.getExame_horai(),
                    e.getExame_horaf()
            });
        }

        tabela.setModel(model);
    }
}