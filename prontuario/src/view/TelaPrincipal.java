package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Menu Principal - Prontuário Médico");
        setSize(480, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Selecione uma opção...", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        root.add(titulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(10, 1, 8, 8));

        // BOTÕES RENOMEADOS PARA "GERENCIAR X"
        JButton btnPaciente       = new JButton("Gerenciar Paciente");
        JButton btnResponsavel    = new JButton("Gerenciar Responsável");
        JButton btnConvenio       = new JButton("Gerenciar Convênio");
        JButton btnExame          = new JButton("Gerenciar Exame");
        JButton btnMedico         = new JButton("Gerenciar Médico");
        JButton btnFuncionario    = new JButton("Gerenciar Funcionário");
        JButton btnMedicamento    = new JButton("Gerenciar Medicamento");
        JButton btnEspecialidade  = new JButton("Gerenciar Especialidade");

        // Botões de Agendamento permanecem iguais
        JButton btnAgendaConsulta = new JButton("Agendar Consulta");
        JButton btnAgendaExame    = new JButton("Agendar Exame");

        painelBotoes.add(btnPaciente);
        painelBotoes.add(btnResponsavel);
        painelBotoes.add(btnConvenio);
        painelBotoes.add(btnExame);
        painelBotoes.add(btnMedico);
        painelBotoes.add(btnFuncionario);
        painelBotoes.add(btnMedicamento);
        painelBotoes.add(btnEspecialidade);
        painelBotoes.add(btnAgendaConsulta);
        painelBotoes.add(btnAgendaExame);

        root.add(painelBotoes, BorderLayout.CENTER);

        JPanel painelSair = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSair = new JButton("Sair");
        painelSair.add(btnSair);
        root.add(painelSair, BorderLayout.SOUTH);

        // Ações dos botões (referências de classe permanecem as mesmas)
        btnPaciente.addActionListener((ActionEvent e) -> new TelaPaciente().setVisible(true));
        btnResponsavel.addActionListener((ActionEvent e) -> new TelaResponsavel().setVisible(true));
        btnConvenio.addActionListener((ActionEvent e) -> new TelaConvenio().setVisible(true));
        btnExame.addActionListener((ActionEvent e) -> new TelaExame().setVisible(true));
        btnMedico.addActionListener((ActionEvent e) -> new TelaMedico().setVisible(true));
        btnFuncionario.addActionListener((ActionEvent e) -> new TelaFuncionario().setVisible(true));
        btnMedicamento.addActionListener((ActionEvent e) -> new TelaMedicamento().setVisible(true));
        btnEspecialidade.addActionListener((ActionEvent e) -> new TelaEspecialidade().setVisible(true));
        btnAgendaConsulta.addActionListener((ActionEvent e) -> new TelaAgendaConsulta().setVisible(true));
        btnAgendaExame.addActionListener((ActionEvent e) -> new TelaAgendaExame().setVisible(true));
        btnSair.addActionListener((ActionEvent e) -> System.exit(0));

        setContentPane(root);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}