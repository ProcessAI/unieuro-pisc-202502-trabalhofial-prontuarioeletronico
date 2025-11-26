package view;

import controller.MedicoController;
import model.Medico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaMedico extends JFrame {
    
    
    private MedicoController controller = new MedicoController();
    
    private JTextField txtCrm = new JTextField(15);
    private JTextField txtNome = new JTextField(15);
    private JTextField txtEspecialidade = new JTextField(15);
    private JTextField txtTelefone = new JTextField(15);
    private JTextField txtEmail = new JTextField(15);
    
    private JButton btnSalvar = new JButton("Salvar");
    private JButton btnBuscar = new JButton("Buscar");
    private JButton btnAtualizar = new JButton("Atualizar");
    private JButton btnExcluir = new JButton("Excluir");
    private JLabel lblMensagem = new JLabel("Status: Aguardando...");

    public TelaMedico() {
        super("Cadastro de Médicos");
        configurarLayout();
        adicionarListeners();
    }

    private void configurarLayout() {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        
        formPanel.add(new JLabel("CRM:"));
        formPanel.add(txtCrm);
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("Especialidade:"));
        formPanel.add(txtEspecialidade);
        formPanel.add(new JLabel("Telefone:"));
        formPanel.add(txtTelefone);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        add(lblMensagem, BorderLayout.NORTH); 

        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    private void adicionarListeners() {
       
        btnSalvar.addActionListener(e -> {
            String crm = txtCrm.getText();
            String nome = txtNome.getText();
            String especialidade = txtEspecialidade.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();
            
            String resultado = controller.salvarMedico(crm, nome, especialidade, telefone, email);
            lblMensagem.setText("Status: " + resultado);
        });

        
        btnBuscar.addActionListener(e -> {
            String crmBusca = txtCrm.getText();
            Medico medico = controller.buscarMedico(crmBusca);
            
            if (medico != null) {
                txtNome.setText(medico.getNome());
                txtEspecialidade.setText(medico.getEspecialidade());
                txtTelefone.setText(medico.getTelefone());
                txtEmail.setText(medico.getEmail());
                lblMensagem.setText("Status: Médico encontrado.");
            } else {
                limparCampos();
                lblMensagem.setText("Status: Médico não encontrado.");
            }
        });
        
        
        btnAtualizar.addActionListener(e -> {
            String crm = txtCrm.getText(); 
            String nome = txtNome.getText();
            String especialidade = txtEspecialidade.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();
            
            String resultado = controller.atualizarMedico(crm, nome, especialidade, telefone, email);
            lblMensagem.setText("Status: " + resultado);
        });

       
        btnExcluir.addActionListener(e -> {
            String crm = txtCrm.getText();
            String resultado = controller.excluirMedico(crm);
            lblMensagem.setText("Status: " + resultado);
            limparCampos();
        });
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtEspecialidade.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
    }

    public static void main(String[] args) {
      
        SwingUtilities.invokeLater(() -> new TelaMedico());
    }

}
