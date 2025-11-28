package view;

import controller.ControllerLogin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;

    public TelaLogin() {
        setTitle("Tela de Login");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        botaoLogin = new JButton("Login");

        // Ação do botão
        botaoLogin.addActionListener((ActionEvent e) -> {
            fazerLogin();
        });

        painel.add(labelUsuario);
        painel.add(campoUsuario);
        painel.add(labelSenha);
        painel.add(campoSenha);
        painel.add(new JLabel()); // espaçamento
        painel.add(botaoLogin);

        add(painel);
    }

    // Método disparado ao clicar no botão - MODIFICADO para abrir TelaPrincipal
    private void fazerLogin() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        ControllerLogin cl = new ControllerLogin();
        boolean autenticado = cl.autenticar(usuario, senha);

        if (autenticado) {
            // Se autenticado, abre a tela principal e fecha a tela de login
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
            this.dispose(); // Fecha a tela de login
        } else {
            // Se falhar, mostra uma mensagem de erro
            JOptionPane.showMessageDialog(this, 
                    "Usuário ou senha inválidos.", 
                    "Erro de Login", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
