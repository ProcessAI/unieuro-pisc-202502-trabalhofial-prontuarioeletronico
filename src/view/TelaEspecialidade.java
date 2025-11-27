// Pasta: view
package view;

import controller.EspecialidadeController;
import model.Especialidade;

import javax.swing.*;
import java.awt.*;

public class TelaEspecialidade extends JFrame {

    // Dependência
    private EspecialidadeController controller = new EspecialidadeController();

    // Componentes de Input
    private JTextField txtId = new JTextField(5);
    private JTextField txtNome = new JTextField(20);
    private JTextField txtStatus = new JTextField(5);
    private JTextField txtCbo = new JTextField(10);
    private JTextField txtEscala = new JTextField(20);
    private JTextArea txtDescricao = new JTextArea(3, 20);
    private JScrollPane scrollDescricao = new JScrollPane(txtDescricao);

    // Componentes de Ação e Status
    private JButton btnSalvar = new JButton("Salvar");
    private JButton btnBuscar = new JButton("Buscar (ID)");
    private JButton btnAtualizar = new JButton("Atualizar");
    private JButton btnExcluir = new JButton("Excluir");
    private JLabel lblMensagem = new JLabel("Status: Pronto.");

    public TelaEspecialidade() {
        super("Cadastro de Especialidades Médicas");
        configurarLayout();
        adicionarListeners();
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10)); // Espaçamento
        
        // Painel de Formulário (Centro)
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.add(new JLabel("ID da Especialidade:"));
        formPanel.add(txtId);
        txtId.setEditable(true); // O ID é inserido para busca/atualização/exclusão, mas é serial no salvar
        
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        
        formPanel.add(new JLabel("Status (1 char):"));
        formPanel.add(txtStatus);
        
        formPanel.add(new JLabel("CBO:"));
        formPanel.add(txtCbo);
        
        formPanel.add(new JLabel("Escala:"));
        formPanel.add(txtEscala);
        
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(scrollDescricao);
        
        // Painel de Botões (Sul)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);

        // Adicionando componentes ao JFrame
        add(lblMensagem, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true);
    }

    private void adicionarListeners() {

        // Ação Salvar
        btnSalvar.addActionListener(e -> {
            String resultado = controller.salvarEspecialidade(
                txtNome.getText(), 
                txtStatus.getText(), 
                txtCbo.getText(), 
                txtEscala.getText(), 
                txtDescricao.getText()
            );
            lblMensagem.setText("Status: " + resultado);
            if (resultado.contains("sucesso")) limparCampos();
        });

        // Ação Buscar
        btnBuscar.addActionListener(e -> {
            Especialidade esp = controller.buscarEspecialidade(txtId.getText());
            if (esp != null) {
                // Preenche os campos
                txtNome.setText(esp.getNome());
                txtStatus.setText(esp.getStatus());
                txtCbo.setText(esp.getCbo());
                txtEscala.setText(esp.getEscala());
                txtDescricao.setText(esp.getDescricao());
                lblMensagem.setText("Status: Especialidade ID " + esp.getId() + " encontrada.");
            } else {
                limparCamposSemId();
                lblMensagem.setText("Status: Especialidade não encontrada ou ID inválido.");
            }
        });

        // Ação Atualizar
        btnAtualizar.addActionListener(e -> {
            String resultado = controller.atualizarEspecialidade(
                txtId.getText(),
                txtNome.getText(), 
                txtStatus.getText(), 
                txtCbo.getText(), 
                txtEscala.getText(), 
                txtDescricao.getText()
            );
            lblMensagem.setText("Status: " + resultado);
        });

        // Ação Excluir
        btnExcluir.addActionListener(e -> {
            String resultado = controller.excluirEspecialidade(txtId.getText());
            lblMensagem.setText("Status: " + resultado);
            if (resultado.contains("sucesso")) limparCampos();
        });
    }

    private void limparCampos() {
        txtId.setText("");
        limparCamposSemId();
    }
    
    private void limparCamposSemId() {
        txtNome.setText("");
        txtStatus.setText("");
        txtCbo.setText("");
        txtEscala.setText("");
        txtDescricao.setText("");
    }

    // Método Main para iniciar a aplicação no NetBeans
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaEspecialidade());
    }
}